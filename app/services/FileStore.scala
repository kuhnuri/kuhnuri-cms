package services

import java.io.{File, FileNotFoundException, IOException}
import java.nio.file.{Files, Path, Paths, StandardCopyOption}
import javax.inject.{Inject, Singleton}

import models._
import play.api.{Configuration, Logger}

import scala.util.{Failure, Success, Try}

@Singleton
class FileStore @Inject()(configuration: Configuration) extends Store {

  private val logger = Logger(this.getClass)
  private val baseDir = configuration.getString("projects.basedir").map(Paths.get(_)).getOrElse(throw new IllegalArgumentException())

  override def retrieve(id: String): Option[Resource] =
    Option(baseDir.resolve(id))
      .filter(Files.exists(_))
      .map(p => Resource(id, p.toFile))

  override def create(newJob: Create): Try[Job] = {
    val file = baseDir.resolve(newJob.id)
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

  override def update(update: Update): Try[Job] = {
    val file = baseDir.resolve(update.id)
    if (Files.exists(file)) {
      try {
        Files.copy(update.data, file, StandardCopyOption.REPLACE_EXISTING)
        Success(Job(update.id))
      } catch {
        case e: IOException => Failure(e)
      }
    } else {
      Failure(new IOException(s"File $file does not exist"))
    }
  }

  override def delete(id: String): Try[Job] = {
    val file = baseDir.resolve(id)
    if (Files.exists(file)) {
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

  private val LOCK_POSTFIX = ".lock"

  override def getLock(id: String): Try[Option[Job]] = {
    val file = baseDir.resolve(id + LOCK_POSTFIX)
    if (Files.exists(file)) {
      try {
        Success(Some(Job(id)))
      } catch {
        case e: IOException => Failure(e)
      }
    } else {
      Success(None)
    }
  }

  override def lock(id: String): Try[Job] = {
    val file = baseDir.resolve(id + LOCK_POSTFIX)
    if (!Files.exists(file)) {
      try {
        Files.createFile(file)
        Success(Job(id))
      } catch {
        case e: IOException => Failure(e)
      }
    } else {
      Failure(new IOException(s"File $file already locked"))
    }
  }

  override def unlock(id: String): Try[Job] = {
    val file = baseDir.resolve(id + LOCK_POSTFIX)
    if (Files.exists(file)) {
      try {
        Files.delete(file)
        Success(Job(id))
      } catch {
        case e: IOException => Failure(e)
      }
    } else {
      Failure(new IOException(s"File $file not locked"))
    }
  }

  override def list(id: String): Try[Option[ResourceMetadata]] = {
    val file = baseDir.resolve(id)
    if (Files.exists(file)) {
      try {
        if (Files.isDirectory(file)) {
          val resources: List[ResourceMetadata] = file.toFile().listFiles()
            .filter(!_.getName.endsWith(LOCK_POSTFIX))
            .map(resourceShallow)
            .toList
          val resource = DirectoryMetadata(getName(file), resources)
          Success(Some(resource))
        } else {
          Failure(new IOException(s"Directory $file not a directory"))
        }
      } catch {
        case e: IOException => Failure(e)
      }
    } else {
      Success(None)
//      Failure(new FileNotFoundException(file.toString))
    }
  }

  private def resourceShallow(f: File): ResourceMetadata =
    if (f.isDirectory) {
      DirectoryMetadata(
        getName(f),
        null)
    } else {
      FileMetadata(
        getName(f),
        new File(f.getAbsolutePath + LOCK_POSTFIX).exists())
    }

  private def getName(f: Path): String = {
    f.getFileName.toString
  }

  private def getName(f: File): String = {
    getName(f.toPath)
  }

}




