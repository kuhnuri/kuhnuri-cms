package controllers.v1

import javax.inject._

import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._
import services.Store

import scala.util.{Failure, Success}

/**
  * Controller for queue client communication
  */
@Singleton
class ListController @Inject()(queue: Store) extends Controller {

  private val logger = Logger(this.getClass)

  def list(id: String) = Action {
    queue.list(id) match {
      case Success(list) => {
        Ok(Json.toJson(list))
      }
      case Failure(e) => NotFound(e.toString)
    }
  }

}