package models

import java.time.LocalDateTime

import play.api.libs.functional.syntax._
import play.api.libs.json._

sealed case class History(
  url: String,
  version: Int,
  revision: Int,
  changeDate: LocalDateTime
)

object History {

  import models.Type._

//  implicit val fileHistoryReads: Reads[History] = (
//    (JsPath \ "name").read[String] and
//      (JsPath \ "type").read[Int] and
//      (JsPath \ "locked").read[Boolean]
//    ) (FileMetadata.apply _)

  implicit val fileMetadataWrites: Writes[History] = new Writes[History] {
    override def writes(file: History): JsValue = Json.obj(
      "url" -> ("argon:" + file.url),
      "version" -> file.version,
      "revision" -> file.revision,
      "changeDate" -> file.changeDate
    )
  }

  //  implicit val ResourceReads: Reads[History] = (
  //    (JsPath \ "name").read[String] and
  //      (JsPath \ "type").read[Type] and
  //      (JsPath \ "locked").read[Boolean]
  //    ) (History.apply _)

//  implicit val directoryMetadataWrites: Writes[DirectoryMetadata] = new Writes[DirectoryMetadata] {
//    override def writes(file: DirectoryMetadata): JsValue = {
//      var res = Json.obj(
//        "name" -> file.name,
//        "type" -> file.resourceType.toString
//      )
//      if (file.children != null) {
//        res = res.+("children" -> JsArray(file.children.map(Json.toJson(_))))
//      }
//      res
//    }
//  }
//
//  implicit val resourceMetadataWrites: Writes[History] = new Writes[History] {
//    override def writes(file: History): JsValue = file match {
//      case d: DirectoryMetadata => Json.toJson(d)(directoryMetadataWrites)
//      case f: FileMetadata => Json.toJson(f)(fileMetadataWrites)
//    }

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
//  }

}
