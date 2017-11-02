package controllers.v1

import java.util.UUID

//import filters.TokenAuthorizationFilter._
import models._
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.{Action, Controller, Results}
//import services.WorkerStore

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AuthenticationController extends Controller {

//  private val logger = Logger(this.getClass)
//
//  // FIXME check password to verify worker is authorized
//  def login = Action.async { request =>
//    request.body.asJson.map { json =>
//      json.validate[Register] match {
//        case req: JsSuccess[Register] => {
//          val worker = Worker(UUID.randomUUID().toString, req.value.id, req.value.uri)
//          logger.info(s"Register worker $req with token ${worker.token}")
//          WorkerStore.workers += worker.token -> worker
//          logger.debug(s"Workers ${WorkerStore.workers}")
//          Future(Ok.withHeaders(AUTH_TOKEN_HEADER -> worker.token))
//        }
//        case e: JsError => Future(BadRequest("Detected error :" + JsError.toJson(e).toString))
//      }
//    }.getOrElse {
//      Future(BadRequest("Expecting Json data"))
//    }
//  }
//
//  def logout = Action.async { request =>
//    request.headers.get(AUTH_TOKEN_HEADER) match {
//      case Some(token) if WorkerStore.workers.get(token).isDefined =>
//        logger.info(s"Unregister Worker with token $token")
//        WorkerStore.workers -= token
//        logger.debug(s"Workers ${WorkerStore.workers}")
//        Future(Results.Ok)
//      case Some(token) =>
//        logger.info(s"Unrecognized API token $token")
//        logger.debug(s"Workers ${WorkerStore.workers}")
//        Future(Results.Unauthorized)
//      case None =>
//        logger.info("Missing API token")
//        Future(Results.Unauthorized)
//    }
//  }

}
