package filters

import javax.inject._

import akka.stream.Materializer
import play.api.Logger
import play.api.mvc._
//import services.WorkerStore

import scala.concurrent.{ExecutionContext, Future}

//@Singleton
//class TokenAuthorizationFilter @Inject()(
//                                          implicit override val mat: Materializer,
//                                          exec: ExecutionContext) extends Filter {
//
//  import TokenAuthorizationFilter._
//
//  private val logger = Logger(this.getClass)
//
//  override def apply(nextFilter: RequestHeader => Future[Result])
//                    (requestHeader: RequestHeader): Future[Result] = {
//    // FIXME all paths should be authorized, not just worker routes
//    if (requestHeader.path.startsWith("/api/v1/work")) {
//      requestHeader.headers.get(AUTH_TOKEN_HEADER) match {
//        case Some(token) if WorkerStore.workers.get(token).isDefined =>
//          nextFilter(requestHeader)
//        case Some(token) =>
//          logger.info(s"Unrecognized API token $token")
//          logger.info(s"Workers ${WorkerStore.workers}")
//          Future(Results.Unauthorized)
//        case None =>
//          logger.info("Missing API token")
//          Future(Results.Unauthorized)
//      }
//    } else {
//      nextFilter(requestHeader)
//    }
//  }
//}
//
//object TokenAuthorizationFilter {
//  val AUTH_TOKEN_HEADER = "X-Auth-Token"
//}