package services

import models._

import scala.util.{Failure, Try}

class WorkerPublisher extends Publisher {

  override def retrieve(id: String, pub: String): Option[Publication] = {
    None
  }

  override def create(job: Create): Try[Publication] = {
    Failure(new UnsupportedOperationException)
  }

  override def delete(id: String, pub: String): Try[Publication] = {
    Failure(new UnsupportedOperationException)
  }

  override def list(id: String): List[Publication] = {
    List.empty
  }

}
