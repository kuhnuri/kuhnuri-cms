package models

import java.time.LocalDateTime

import play.api.libs.functional.syntax._
import play.api.libs.json._

trait Node {
  def name: String

  def path: String

  def resourceType: Type
}

trait Parent {
  def children: List[Node]
}

trait Metadata {
  def created: LocalDateTime

  def author: String
}

object Node {

  import models.Type._

  implicit val fileMetadataReads: Reads[FileNode] = (
    (JsPath \ "name").read[String] and
      //      (JsPath \ "type").read[Type] and
      (JsPath \ "locked").read[Boolean] and
      (JsPath \ "path").read[String]
    ) (FileNode.apply _)

  implicit val fileMetadataWrites: Writes[FileNode] = new Writes[FileNode] {
    override def writes(file: FileNode): JsValue = Json.obj(
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

  implicit val directoryMetadataWrites: Writes[DirectoryNode] = new Writes[DirectoryNode] {
    override def writes(file: DirectoryNode): JsValue = {
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

  implicit val projectMetadataWrites: Writes[Project] = new Writes[Project] {
    override def writes(file: Project): JsValue = {
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

  implicit val resourceMetadataWrites: Writes[Node] = new Writes[Node] {
    override def writes(file: Node): JsValue = file match {
      case p: Project => Json.toJson(p)(projectMetadataWrites)
      case d: DirectoryNode => Json.toJson(d)(directoryMetadataWrites)
      case f: FileNode => Json.toJson(f)(fileMetadataWrites)
      case f: Info => Json.toJson(f)(infoWrites)
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

    implicit val infoWrites: Writes[Info] = (
      (JsPath \ "name").write[String] and
        (JsPath \ "path").write[String] and
        (JsPath \ "author").write[String] and
        (JsPath \ "created").write[LocalDateTime]
      ) (unlift(Info.unapply _))
}

sealed case class Info(name: String, path: String, author: String, created: LocalDateTime) extends Node with Metadata {
  override val resourceType = ResourceType()
}

sealed case class FileNode(name: String, locked: Boolean, path: String) extends Node {
  override val resourceType = ResourceType()
}

sealed case class DirectoryNode(name: String, children: List[Node], path: String) extends Node with Parent {
  override val resourceType = DirectoryType()
}

sealed case class Project(name: String, children: List[Node], path: String, translations: Option[Translations]) extends Node with Parent {
  override val resourceType = ProjectType()
}

sealed case class Translations(master: String, translations: List[String])

sealed case class ProjectConfiguration(val master: Option[String])

object ProjectConfiguration {
  implicit val projectConfigurationReads: Reads[ProjectConfiguration] =
    (JsPath \ "master").readNullable[String]
      .map { name => new ProjectConfiguration(name) }
}

trait Type

object Type {
  implicit val resourceReads: Reads[Type] =
    Reads[Type](j => try {
      JsSuccess(j.as[JsString].value match {
        case "PROJECT" => ProjectType()
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

sealed case class ProjectType() extends Type {
  override def toString = "PROJECT"
}
