package controllers.v1

import java.io.FileNotFoundException
import java.time.LocalDateTime
import javax.inject._

import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._
import services.Store
import models.{History, ProjectMetadata}

import scala.util.{Failure, Success, Try}
import models.ResourceMetadata._
import models.History._

/**
  * Controller for queue client communication
  */
@Singleton
class ListController @Inject()(queue: Store) extends Controller {

  private val logger = Logger(this.getClass)

  def projects() = Action {
    queue.projects() match {
//      case Success(List.empty) => NotFound
      case Success(projects) => Ok(Json.toJson(projects))
      case Failure(e: FileNotFoundException) => NotFound(e.getMessage)
      case Failure(e) => InternalServerError(e.getMessage)
    }
  }

  def list(project: String, id: String) = Action {
    queue.list(project, id) match {
      case Success(Some(resourceMetadata)) => Ok(Json.toJson(resourceMetadata))
      case Success(None) => NotFound
      case Failure(e: FileNotFoundException) => NotFound(e.getMessage)
      case Failure(e) => InternalServerError(e.getMessage)
    }
  }

  def history(project: String, id: String) = Action {
    queue.history(project, id) match {
      case Success(history) => Ok(Json.toJson(history))
      case Failure(e: FileNotFoundException) => NotFound(e.getMessage)
      case Failure(e) => InternalServerError(e.getMessage)
    }
  }

}