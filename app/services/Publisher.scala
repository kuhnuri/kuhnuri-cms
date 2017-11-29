package services

import models._

import scala.util.Try

trait Publisher {

  def retrieve(id: String, pub: String): Option[Publication]

  def create(job: Create): Try[Publication]

  def delete(id: String, pub: String): Try[Publication]

  def list(id: String): List[Publication]

}
