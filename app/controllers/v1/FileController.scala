package controllers.v1

import java.io.ByteArrayInputStream
import javax.inject._

import models._
import play.api.Logger
import play.api.libs.MimeTypes
import play.api.mvc._
import services.Store

import scala.util.{Failure, Success, Try}

/**
  * Controller for queue client communication
  */
@Singleton
class FileController @Inject()(queue: Store) extends Controller {

//  import FileController._

  private val logger = Logger(this.getClass)
  
  def retrieve(project: String, id: String) = Action {
    queue.retrieve(project, id) match {
      case Some(resource) => {
        val mimeType = MimeTypes.forFileName(resource.id).getOrElse("application/xml")
        Ok.sendFile(resource.data, false)
          .as(mimeType)
      }
      case None => NotFound
    }
  }

  def update(project: String, id: String, version: Boolean) = Action { request =>
    request.body.asRaw.flatMap(_.asBytes()) match {
      case Some(bs) => {
        val in = new ByteArrayInputStream(bs.toArray)
        val res: Try[Job] = queue.update(project, Update(id, in, version))
        res match {
          case Success(job) => Ok
          case Failure(e) => e.printStackTrace(); InternalServerError("Failed to update file: " + e.getMessage)
        }
      }
      case None => InternalServerError("File content missing for update")
    }
  }

  def create(project: String, id: String) = Action { request =>
    request.body.asRaw.flatMap(_.asBytes()) match {
      case Some(bs) => {
        val in = new ByteArrayInputStream(bs.toArray)
        val res: Try[Job] = queue.create(project, Create(id, in))
        res match {
          case Success(job) => Ok
          case Failure(e) => InternalServerError("Failed to create file: " + e.getMessage)
        }
      }
      case None => InternalServerError("File content missing for create")
    }
  }

  def delete(project: String, id: String) = Action {
    queue.delete(project, id) match {
      case Success(job) => Ok
      case Failure(e) => NotFound
    }
  }

  def getLock(project: String, id: String) = Action { request =>
    queue.getLock(project, id) match {
      case Success(Some(job)) => Ok
      case Success(None) => NotFound
      case Failure(e) => InternalServerError("Failed to check lock: " + e.getMessage)
    }
  }

  def lock(project: String, id: String) = Action { request =>
    queue.lock(project, id) match {
      case Success(job) => Ok
      case Failure(e) => InternalServerError("Failed to lock file: " + e.getMessage)
    }
  }

  def unlock(project: String, id: String) = Action {
    queue.unlock(project, id) match {
      case Success(job) => Ok
      case Failure(e) => InternalServerError("Failed to unlock file: " + e.getMessage)
    }
  }

}

//object FileController {
//  implicit val localDateTimeWrites =
//    Writes[LocalDateTime](s => JsString(s.atOffset(ZoneOffset.UTC).toString))
//
//
//  implicit val jobStatusStringReads =
//    Reads[StatusString](j => try {
//      JsSuccess(StatusString.parse(j.as[JsString].value))
//    } catch {
//      case e: IllegalArgumentException => JsError(e.toString)
//    })
//
//  implicit val jobStatusStringWrites =
//    Writes[StatusString](s => JsString(s.toString))
//
//  implicit val createReads: Reads[Create] = (
//    (JsPath \ "input").read[String] /*.filter(new URI(_).isAbsolute)*/ and
//      (JsPath \ "output").read[String] /*.filter(_.map {
//        new URI(_).isAbsolute
//      }.getOrElse(true))*/ and
//      (JsPath \ "transtype").read[String] and
//      (JsPath \ "priority").readNullable[Int] and
//      (JsPath \ "params").read[Map[String, String]]
//    ) (Create.apply _)
//
//  implicit val jobWrites: Writes[Job] = (
//    (JsPath \ "id").write[String] and
//      (JsPath \ "input").write[String] and
//      (JsPath \ "output").write[String] and
//      (JsPath \ "transtype").write[String] and
//      (JsPath \ "params").write[Map[String, String]] and
//      (JsPath \ "status").write[StatusString] and
//      (JsPath \ "priority").write[Int] and
//      (JsPath \ "created").write[LocalDateTime] and
//      (JsPath \ "processing").write[Option[LocalDateTime]] and
//      (JsPath \ "finished").write[Option[LocalDateTime]]
//    ) (unlift(Job.unapply _))
//  implicit val jobReads: Reads[Job] = (
//    (JsPath \ "id").read[String] and
//      (JsPath \ "input").read[String] /*.filter(new URI(_).isAbsolute)*/ and
//      (JsPath \ "output").read[String] /*.filter(_.map {
//        new URI(_).isAbsolute
//      }.getOrElse(true))*/ and
//      (JsPath \ "transtype").read[String] and
//      (JsPath \ "params").read[Map[String, String]] and
//      (JsPath \ "status").read[StatusString] and
//      (JsPath \ "priority").read[Int] and
//      (JsPath \ "created").read[LocalDateTime] and
//      (JsPath \ "processing").readNullable[LocalDateTime] and
//      (JsPath \ "finished").readNullable[LocalDateTime]
//    ) (Job.apply _)
//
//  implicit val jobResultReads: Reads[JobResult] = (
//    (JsPath \ "job").read[Job] and
//      (JsPath \ "log").read[Seq[String]]
//    ) (JobResult.apply _)
//
//  //  implicit val jobStatusWrites: Writes[JobStatus] = (
//  //    (JsPath \ "id").write[String] and
//  //      (JsPath \ "output").writeNullable[String] and
//  //      (JsPath \ "status").write[StatusString]
//  //    ) (unlift(JobStatus.unapply _))
//}
