package services

import models._

import scala.util.Try

trait Store {
  def retrieve(id: String): Option[Resource]

  def create(job: Create): Try[Job]

//  def contents(): List[Job]

  def update(job: Update): Try[Job]

  def delete(id: String): Try[Job]
}
