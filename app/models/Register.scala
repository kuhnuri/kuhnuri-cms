package models

import java.net.{URI, URISyntaxException}

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Register(id: String, uri: URI)

object Register {

  implicit val uriReads = Reads { js =>
    js match {
      case JsString(s) => try {
        JsSuccess(new URI(s))
      } catch {
        case e: URISyntaxException => JsError(s"Unable to parse URI: ${e.getMessage}")
      }
      case _ => JsError("JsString expected to convert to URI")
    }
  }

  implicit val registerReads: Reads[Register] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "uri").read[URI]
    ) (Register.apply _)
}