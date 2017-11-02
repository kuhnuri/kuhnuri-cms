//import models.StatusString.Queue
import org.scalatest.TestData
import org.scalatestplus.play._
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsArray, JsObject, JsString, JsValue}
import play.api.test.Helpers.{contentAsJson, contentType, _}
import play.api.test._
import services.{DummyQueue, Store}

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class WorkSpec extends PlaySpec with OneAppPerTest {

//  implicit override def newAppForTest(testData: TestData): Application =  new GuiceApplicationBuilder()
////    .configure(Map("ehcacheplugin" -> "disabled"))
//    .overrides(bind(classOf[Dispatcher]).to(classOf[DummyQueue]))
//    .build()
//
////  implicit override lazy val app = new GuiceApplicationBuilder()
////    .overrides(bind(classOf[Queue]).to(classOf[DummyQueue]))
//////    .overrides(bind(classOf[Dispatcher]).to(classOf[DefaultDispatcher]))
////    .build
//
////  def sortById(arr: JsValue): JsArray = {
////    JsArray(arr.as[JsArray].value.toList.sortBy(v => (v \ "id").get.as[JsString].value))
////  }
//
//  "WorkController" should {
//
//    "return job" in {
//      val query = JsArray(List(
//        JsString("html5")
//      ))
//      val first = route(app, FakeRequest(POST, "/api/v1/work").withJsonBody(query)).get
//
//      status(first) mustBe OK
//      contentType(first) mustBe Some("application/json")
//      contentAsJson(first) mustEqual JsObject(Map(
//        "id" -> JsString("id-A1"),
//        "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//        "output" -> JsString("file:/Volumes/tmp/out"),
//        "transtype" -> JsString("html5"),
//        "params" -> JsObject(List.empty)
//      ))
//
//      val second = route(app, FakeRequest(POST, "/api/v1/work").withJsonBody(query)).get
//
//      status(second) mustBe OK
//      contentType(second) mustBe Some("application/json")
//      contentAsJson(second) mustEqual JsObject(Map(
//        "id" -> JsString("id-A"),
//        "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//        "output" -> JsString("file:/Volumes/tmp/out"),
//        "transtype" -> JsString("html5"),
//        "params" -> JsObject(List.empty)
//      ))
//
//      route(app, FakeRequest(POST, "/api/v1/work").withJsonBody(query)).map(status) mustBe Some(NO_CONTENT)
//    }
//
//    "return PDF job" in {
//      val query = JsArray(List(
//        JsString("pdf")
//      ))
//      val home = route(app, FakeRequest(POST, "/api/v1/work").withJsonBody(query)).get
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("application/json")
//      contentAsJson(home) mustEqual JsObject(Map(
//        "id" -> JsString("id-B"),
//        "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//        "output" -> JsString("file:/Volumes/tmp/out"),
//        "transtype" -> JsString("pdf"),
//        "params" -> JsObject(List.empty)
//      ))
//    }
//
//    "return XHTML job" in {
//      val query = JsArray(List(
//        JsString("xhtml")
//      ))
//
//      val home = route(app, FakeRequest(POST, "/api/v1/work").withJsonBody(query)).get
//
//      val text = contentAsString(home)
//
//      status(home) mustBe NO_CONTENT
//    }
//  }
//
////  "ListController" should {
////
////    "list jobs" in {
////      val home = route(app, FakeRequest(GET, "/api/v1/jobs")).get
////
////      status(home) mustBe OK
////      contentType(home) mustBe Some("application/json")
////      sortById(contentAsJson(home)) mustEqual JsArray(Seq(
////        JsObject(Map(
////          "id" -> JsString("id-A"),
////          "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
////          "output" -> JsString("file:/Volumes/tmp/out"),
////          "transtype" -> JsString("html5"),
////          "params" -> JsObject(List.empty)
////        )),
////        JsObject(Map(
////          "id" -> JsString("id-B"),
////          "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
////          "output" -> JsString("file:/Volumes/tmp/out"),
////          "transtype" -> JsString("pdf"),
////          "params" -> JsObject(List.empty)
////        )
////        )))
////    }
////
////    "show job details" in {
////      val home = route(app, FakeRequest(GET, "/api/v1/job/id-B")).get
////
////      status(home) mustBe OK
////      contentType(home) mustBe Some("application/json")
////      contentAsJson(home) mustEqual JsObject(Map(
////        "id" -> JsString("id-B"),
////        "output" -> JsString("file:/Volumes/tmp/out"),
////        "status" -> JsString(Queue.toString)
////      ))
////    }
////
////    "send 404 on a missing job" in {
////      route(app, FakeRequest(GET, "/api/v1/job/X")).map(status(_)) mustBe Some(NOT_FOUND)
////    }
////
////    "add new job" should {
////
////      "add" in {
////        val body = JsObject(Map(
////          "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
////          "output" -> JsString("file:/Volumes/tmp/out"),
////          "transtype" -> JsString("html5"),
////          "params" -> JsObject(List.empty)
////        ))
////        val created = route(app, FakeRequest(POST, "/api/v1/job").withJsonBody(body)).get
////
////        status(created) mustBe CREATED
////        contentType(created) mustBe Some("application/json")
////        //        contentAsJson(created) mustEqual JsObject(Map(
////        //            "id" -> JsString("id-A"),
////        //            "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
////        //            "output" -> JsString("file:/Volumes/tmp/out"),
////        //            "transtype" -> JsString("html5"),
////        //            "params" -> JsObject(List.empty)
////        //          ))
////
////        val res = route(app, FakeRequest(GET, "/api/v1/jobs")).map(contentAsJson(_)).get.as[JsArray]
////        res.value.size mustBe 3
////      }
////    }
////  }

}
