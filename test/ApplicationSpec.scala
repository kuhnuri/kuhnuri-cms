//import models.StatusString.Queue
import org.scalatestplus.play._
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
class ApplicationSpec extends PlaySpec with OneAppPerSuite {
//
//  implicit override lazy val app = new GuiceApplicationBuilder()
//    .overrides(bind(classOf[Store]).to(classOf[DummyQueue]))
//    //    .overrides(bind(classOf[Dispatcher]).to(classOf[DefaultDispatcher]))
//    .build
//
//  "Routes" should {
//
//    "send 404 on a bad request" in {
//      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
//    }
//  }
//
//  def sortById(arr: JsValue): JsArray = {
//    JsArray(arr.as[JsArray].value.toList.sortBy(v => (v \ "id").get.as[JsString].value))
//  }
//
//  "ListController" should {
//
//    "list jobs" in {
//      val home = route(app, FakeRequest(GET, "/api/v1/jobs")).get
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("application/json")
//      sortById(contentAsJson(home)) mustEqual JsArray(Seq(
//        JsObject(Map(
//          "id" -> JsString("id-A"),
//          "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//          "output" -> JsString("file:/Volumes/tmp/out"),
//          "transtype" -> JsString("html5"),
//          "params" -> JsObject(List.empty),
//          "status" -> JsString("queue")
//        )),
//        JsObject(Map(
//          "id" -> JsString("id-A1"),
//          "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//          "output" -> JsString("file:/Volumes/tmp/out"),
//          "transtype" -> JsString("html5"),
//          "params" -> JsObject(List.empty),
//          "status" -> JsString("queue")
//        )),
//        JsObject(Map(
//          "id" -> JsString("id-B"),
//          "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//          "output" -> JsString("file:/Volumes/tmp/out"),
//          "transtype" -> JsString("pdf"),
//          "params" -> JsObject(List.empty),
//          "status" -> JsString("queue")
//        ))
//      ))
//    }
//
//    "show job details" in {
//      val home = route(app, FakeRequest(GET, "/api/v1/job/id-B")).get
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("application/json")
//      contentAsJson(home) mustEqual JsObject(Map(
//        "id" -> JsString("id-B"),
//        "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//        "output" -> JsString("file:/Volumes/tmp/out"),
//        "transtype" -> JsString("pdf"),
//        "params" -> JsObject(List.empty),
//        "status" -> JsString("queue")
//      ))
//    }
//
//    "send 404 on a missing job" in {
//      route(app, FakeRequest(GET, "/api/v1/job/X")).map(status(_)) mustBe Some(NOT_FOUND)
//    }
//
//    "add new job" should {
//
//      "add" in {
//        val body = JsObject(Map(
//          "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//          "output" -> JsString("file:/Volumes/tmp/out"),
//          "transtype" -> JsString("html5"),
//          "params" -> JsObject(List.empty)
//        ))
//        val created = route(app, FakeRequest(POST, "/api/v1/job").withJsonBody(body)).get
//
//        status(created) mustBe CREATED
//        contentType(created) mustBe Some("application/json")
//        //        contentAsJson(created) mustEqual JsObject(Map(
//        //            "id" -> JsString("id-A"),
//        //            "input" -> JsString("file:/Users/jelovirt/Work/github/dita-ot/src/main/docsrc/userguide.ditamap"),
//        //            "output" -> JsString("file:/Volumes/tmp/out"),
//        //            "transtype" -> JsString("html5"),
//        //            "params" -> JsObject(List.empty)
//        //          ))
//
//        val res = route(app, FakeRequest(GET, "/api/v1/jobs")).map(contentAsJson(_)).get.as[JsArray]
//        res.value.size mustBe 4
//      }
//    }
//  }
//
//  //  "CountController" should {
//  //
//  //    "return an increasing count" in {
//  //      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "0"
//  //      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "1"
//  //      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "2"
//  //    }
//  //
//  //  }

}
