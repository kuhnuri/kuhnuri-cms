package controllers.v1

import java.io.{ByteArrayInputStream, FileNotFoundException}
import javax.inject._

import models.History._
import models.{Create, Job}
import models.Node._
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._
import services.{Publisher, Store}

import scala.util.{Failure, Success, Try}

/**
  * Controller for queue client communication
  */
@Singleton
class PublisherController @Inject()(queue: Publisher) extends Controller {

  private val logger = Logger(this.getClass)

  def retrieve(id: String, pub: String) = Action {
//    Ok(Json.toJson(queue.list(id)))
    InternalServerError
  }

  def create(id: String) = Action { request =>
    request.body.asRaw.flatMap(_.asBytes()) match {
      case Some(bs) => {
        val in = new ByteArrayInputStream(bs.toArray)
        val res: Try[Job] = Failure(new UnsupportedOperationException)//queue.create(Create(id, in))
        res match {
          case Success(job) => Ok
          case Failure(e) => InternalServerError("Failed to create publication: " + e.getMessage)
        }
      }
      case None => InternalServerError("File content missing for create")
    }
  }

  def delete(id: String, pub: String) = Action {
    queue.delete(id, pub) match {
      case Success(job) => Ok
      case Failure(e) => NotFound
    }
  }

}