package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

trait ResourceMetadata {
  def name: String

  def resourceType: Type
}

object ResourceMetadata {

  import models.Type._

  implicit val fileMetadataReads: Reads[FileMetadata] = (
    (JsPath \ "name").read[String] and
      //      (JsPath \ "type").read[Type] and
      (JsPath \ "locked").read[Boolean] and
      (JsPath \ "path").read[String]
    ) (FileMetadata.apply _)

  implicit val fileMetadataWrites: Writes[FileMetadata] = new Writes[FileMetadata] {
    override def writes(file: FileMetadata): JsValue = Json.obj(
      "name" -> file.name,
      "type" -> file.resourceType.toString,
      "path" -> file.path,
      "locked" -> file.locked
    )
  }

  //  implicit val ResourceReads: Reads[ResourceMetadata] = (
  //    (JsPath \ "name").read[String] and
  //      (JsPath \ "type").read[Type] and
  //      (JsPath \ "locked").read[Boolean]
  //    ) (ResourceMetadata.apply _)

  implicit val directoryMetadataWrites: Writes[DirectoryMetadata] = new Writes[DirectoryMetadata] {
    override def writes(file: DirectoryMetadata): JsValue = {
      var res = Json.obj(
        "name" -> file.name,
        "path" -> file.path,
        "type" -> file.resourceType.toString
      )
      if (file.children != null) {
        res = res.+("children" -> JsArray(file.children.map(Json.toJson(_))))
      }
      res
    }
  }

  implicit val resourceMetadataWrites: Writes[ResourceMetadata] = new Writes[ResourceMetadata] {
    override def writes(file: ResourceMetadata): JsValue = file match {
      case d: DirectoryMetadata => Json.toJson(d)(directoryMetadataWrites)
      case f: FileMetadata => Json.toJson(f)(fileMetadataWrites)
    }

    //
    //
    //      Json.obj(
    //      "name" -> file.name,
    //      "type" -> file.resourceType.toString,
    //      //      "children" -> Json.toJson(file.children)
    //      "children" -> file match {
    //        case DirectoryMetadata(name, children) => "foo"//JsArray(children.map(Json.toJson()(_)))
    //        case FileMetadata => "foo"//JsNull
    //      }
    //      //      "locked" -> file.locked
    //    )
  }

}

sealed case class FileMetadata(name: String, locked: Boolean, path: String) extends ResourceMetadata {
  override val resourceType = ResourceType()
}

sealed case class DirectoryMetadata(name: String, children: List[ResourceMetadata], path: String) extends ResourceMetadata {
  override val resourceType = DirectoryType()
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
