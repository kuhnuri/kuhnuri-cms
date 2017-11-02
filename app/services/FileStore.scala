package services

import java.io.IOException
import java.nio.file.{Files, Paths, StandardCopyOption}
import javax.inject.{Inject, Singleton}

import models._
import play.api.{Configuration, Logger}

import scala.util.{Failure, Success, Try}

@Singleton
class FileStore @Inject()(configuration: Configuration) extends Store {

  private val logger = Logger(this.getClass)
  private val baseDir = configuration.getString("projects.basedir").map(Paths.get(_)).get

  override def retrieve(id: String): Option[Resource] =
    Option(baseDir.resolve(id))
      .filter(Files.exists(_))
      .map(p => new Resource(id, p.toFile))

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

  private val LOCK_PREFIX = ".lock"

  override def lock(id: String): Try[Job] = {
    val file = baseDir.resolve(id + LOCK_PREFIX)
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
    val file = baseDir.resolve(id + LOCK_PREFIX)
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

}




