package services

import java.io.{File, IOException}
import java.nio.file.{Files, Path, Paths, StandardCopyOption}
import java.time.LocalDateTime
import javax.inject.{Inject, Singleton}

import models.ProjectConfiguration._
import models._
import play.api.libs.json.Json
import play.api.{Configuration, Logger}

import scala.util.{Failure, Success, Try}

@Singleton
class FileStore @Inject()(configuration: Configuration) extends Store {

  private val LOCK_POSTFIX = ".lock"
  private val STAGE_POSTFIX = ".stage"

  private val logger = Logger(this.getClass)
  private val baseDir = configuration.getString("projects.basedir").map(Paths.get(_)).getOrElse(throw new IllegalArgumentException())
  private val projectMetadatas: Map[String, ProjectMetadata] = readProjects(baseDir)

  private def readProjects(baseDir: Path): Map[String, ProjectMetadata] = {
    if (Files.exists(baseDir)) {
      try {
        if (Files.isDirectory(baseDir)) {
          baseDir.toFile().listFiles()
            .filter(f => f.isDirectory && fileFilter(f))
            .map(f => f.getName -> readProject(f))
            .toMap
        } else {
          throw new IllegalArgumentException(s"$baseDir not a directory")
        }
      } catch {
        case e: IOException => Map.empty
      }
    } else {
      Map.empty
    }
  }

  private def readProject(f: File): ProjectMetadata = {
    val config = f.toPath.resolve(".project.json")
    val projectConfiguration: ProjectConfiguration =
      if (Files.exists(config)) {
        Json.fromJson(Json.parse(Files.readAllBytes(config)))
          .getOrElse(new ProjectConfiguration(Option.empty))
      } else {
        new ProjectConfiguration(Option.empty)
      }
    val translations =
      projectConfiguration.master match {
        case Some(master) => Some(new Translations(master, List.empty))
        case None => None
      }
    val root = translations match {
      case Some(translations) => f.toPath.resolve(translations.master).toFile
      case None => f
    }
    val children = root.listFiles()
      .filter(fileFilter)
      .map(resourceShallow(root.toPath, _))
      .toList
    ProjectMetadata(
      getName(f),
      children,
      f.getName,
      translations
    )
  }

  override def retrieve(project: String, id: String): Option[Resource] =
    projectRootDir(project)
      .map(dir => dir.resolve(id))
      .filter(Files.exists(_))
      .map(p => Resource(id, p.toFile))

  override def create(project: String, newJob: Create): Try[Job] = {
    projectRootDir(project) match {
      case Some(dir) => {
        val file = dir.resolve(newJob.id)
        if (!Files.exists(file)) {
          try {
            Files.copy(newJob.data, file)
            Success(Job(newJob.id))
          } catch {
            case e: IOException => Failure(e)
          }
        } else {
          Failure(new IOException(s"File $file already exists"))
        }
      }
      case None => Failure(new IOException(s"Project $project not found"))
    }
  }

  override def update(project: String, update: Update): Try[Job] = {
    projectRootDir(project) match {
      case Some(dir) => {
        val lock = dir.resolve(update.id + LOCK_POSTFIX)
        val stage = dir.resolve(update.id + STAGE_POSTFIX)
        if (Files.exists(lock) && Files.exists(stage)) {
          try {
            Files.copy(update.data, stage, StandardCopyOption.REPLACE_EXISTING)
            Success(Job(update.id))
          } catch {
            case e: IOException => Failure(e)
          }
        } else {
          Failure(new IOException(s"File $stage does not exist"))
        }
      }
      case None => Failure(new IOException(s"Project $project not found"))
    }
  }

  override def delete(project: String, id: String): Try[Job] = {
    projectRootDir(project) match {
      case Some(dir) => {
        val file = dir.resolve(id)
        val lock = dir.resolve(id + LOCK_POSTFIX)
        if (Files.exists(file) && !Files.exists(lock)) {
          try {
            Files.delete(file)
            Success(Job(id))
          } catch {
            case e: IOException => Failure(e)
          }
        } else {
          Failure(new IOException(s"File $file does not exist"))
        }
      }
      case None => Failure(new IOException(s"Project $project not found"))
    }
  }

  override def getLock(project: String, id: String): Try[Option[Job]] = {
    projectRootDir(project) match {
      case Some(dir) => {
        val lock = dir.resolve(id + LOCK_POSTFIX)
        if (Files.exists(lock)) {
          try {
            Success(Some(Job(id)))
          } catch {
            case e: IOException => Failure(e)
          }
        } else {
          Success(None)
        }
      }
      case None => Failure(new IOException(s"Project $project not found"))
    }
  }

  override def lock(project: String, id: String): Try[Job] = {
    projectRootDir(project) match {
      case Some(dir) => {
        val file = dir.resolve(id)
        val lock = dir.resolve(id + LOCK_POSTFIX)
        val stage = dir.resolve(id + STAGE_POSTFIX)
        if (!Files.exists(lock)) {
          try {
            Files.createFile(lock)
            Files.copy(file, stage, StandardCopyOption.REPLACE_EXISTING)
            Success(Job(id))
          } catch {
            case e: IOException => Failure(e)
          }
        } else {
          Failure(new IOException(s"File $lock already locked"))
        }
      }
      case None => Failure(new IOException(s"Project $project not found"))
    }
  }

  override def unlock(project: String, id: String): Try[Job] = {
    projectRootDir(project) match {
      case Some(dir) => {
        val file = dir.resolve(id)
        val lock = dir.resolve(id + LOCK_POSTFIX)
        val stage = dir.resolve(id + STAGE_POSTFIX)
        if (Files.exists(lock)) {
          try {
            Files.move(stage, file, StandardCopyOption.REPLACE_EXISTING)
            Files.delete(lock)
            Success(Job(id))
          } catch {
            case e: IOException => Failure(e)
          }
        } else {
          Failure(new IOException(s"File $lock not locked"))
        }
      }
      case None => Failure(new IOException(s"Project $project not found"))
    }
  }

  override def projects(): Try[List[ProjectMetadata]] = Success(projectMetadatas.values.toList)

  private def projectRootDir(project: String): Option[Path] = {
    projectMetadatas.get(project) match {
      case Some(project) => {
        Some(projectRootDir(project))
      }
      case None => None
    }
  }

  private def projectRootDir(project: ProjectMetadata): Path = {
    project.translations match {
      case Some(translations) => baseDir.resolve(project.path).resolve(translations.master)
      case None => baseDir.resolve(project.path)
    }
  }

  override def list(project: String, id: String): Try[Option[ResourceMetadata]] = {
    projectMetadatas.get(project) match {
      case Some(project) => {
        val projectDir = projectRootDir(project)
        val file = projectDir.resolve(id)
        if (Files.exists(file)) {
          try {
            if (Files.isDirectory(file)) {
              val resources: List[ResourceMetadata] = file.toFile().listFiles()
                .filter(fileFilter)
                .map(resourceShallow(projectDir, _))
                .toList
              val resource = DirectoryMetadata(getName(file), resources, id)
              Success(Some(resource))
            } else {
              Failure(new IOException(s"Directory $file not a directory"))
            }
          } catch {
            case e: IOException => Failure(e)
          }
        } else {
          Success(None)
        }
      }
      case None => Success(None)
    }
  }

  private def fileFilter(f: File): Boolean = {
    val name = f.getName
    !(name.endsWith(LOCK_POSTFIX) ||
      name.endsWith(STAGE_POSTFIX) ||
      name.startsWith("."))
  }

  override def history(project: String, id: String): Try[List[History]] = {
    projectRootDir(project) match {
      case Some(dir) => {
        val file = dir.resolve(id)
        if (Files.exists(file)) {
          try {
            if (!Files.isDirectory(file)) {
              Success(List(History(id, 1, 1, LocalDateTime.now())))
            } else {
              Failure(new IOException(s"Path $file is a directory"))
            }
          } catch {
            case e: IOException => Failure(e)
          }
        } else {
          Success(List.empty)
        }
      }
      case None => Failure(new IOException(s"Project $project not found"))
    }
  }

  private def resourceShallow(projectDir: Path, f: File): ResourceMetadata =
    if (f.isDirectory) {
      DirectoryMetadata(
        getName(f),
        null,
        projectDir.relativize(f.toPath).toString)
    } else {
      FileMetadata(
        getName(f),
        new File(f.getAbsolutePath + LOCK_POSTFIX).exists(),
        projectDir.relativize(f.toPath).toString)
    }

  private def getName(f: Path): String = {
    f.getFileName.toString
  }

  private def getName(f: File): String = {
    getName(f.toPath)
  }

}




