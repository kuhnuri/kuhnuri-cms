package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

sealed case class ResourceMetadata(name: String, resourceType: Type, locked: Boolean)

object ResourceMetadata {

  import models.Type._

  implicit val ResourceReads: Reads[ResourceMetadata] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "type").read[Type] and
      (JsPath \ "locked").read[Boolean]
    ) (ResourceMetadata.apply _)

  implicit val jobWrites: Writes[ResourceMetadata] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "type").write[Type] and
      (JsPath \ "locked").write[Boolean]
    ) (unlift(ResourceMetadata.unapply _))
}

trait Type

object Type {
  implicit val resourceReads: Reads[Type] =
    Reads[Type](j => try {
      JsSuccess(j.as[JsString].value match {
        case "RESOURCE" => ResourceType()
        case "DIRECTORY" => DirectoryType()
        case v => throw new IllegalArgumentException(v.toString)
      })
    } catch {
      case e: IllegalArgumentException => JsError(e.toString)
    })

  implicit val typeWrites: Writes[Type] =
    Writes[Type](s => JsString(s.toString))
}

sealed case class ResourceType() extends Type {
  override def toString = "RESOURCE"
}

sealed case class DirectoryType() extends Type {
  override def toString = "DIRECTORY"
}
