package services

import models._

import scala.util.Try

trait Store {
  def retrieve(project: String, id: String): Option[Resource]

  def create(project: String, job: Create): Try[Job]

//  def contents(): List[Job]

  def update(project: String, job: Update): Try[Job]

  def delete(project: String, id: String): Try[Job]

  def getLock(project: String, id: String): Try[Option[Job]]

  def lock(project: String, id: String): Try[Job]

  def unlock(project: String, id: String): Try[Job]

  def projects(): Try[List[ProjectMetadata]]

  def list(project: String, id: String): Try[Option[ResourceMetadata]]

  def history(project: String, id: String): Try[List[History]]

}
