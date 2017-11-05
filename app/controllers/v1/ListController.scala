package controllers.v1

import java.io.FileNotFoundException
import javax.inject._

import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._
import services.Store

import scala.util.{Failure, Success}
import models.ResourceMetadata._

/**
  * Controller for queue client communication
  */
@Singleton
class ListController @Inject()(queue: Store) extends Controller {

  private val logger = Logger(this.getClass)

  def list(id: String) = Action {
    queue.list(id) match {
      case Success(resourceMetadata) => Ok(Json.toJson(resourceMetadata))
      case Failure(e: FileNotFoundException) => NotFound(e.getMessage)
      case Failure(e) => InternalServerError(e.getMessage)
    }
  }

}