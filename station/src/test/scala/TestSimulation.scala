import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TestSimulation extends Simulation {
  val URI = "/observe"

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json;charset=UTF-8")

  val scn = scenario("TestSimulation")
    .exec(http("request_1")
      .get(URI).queryParam("delay", 20).check(status.is(200)))
    .pause(5)

  val repeatScn = scenario("LoadSimulation").exec(
    repeat(30) {
      exec(
        http("request_2")
          .get(URI).queryParam("delay", 20).check(status.is(200)))
        .pause(5)
    }
  )

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)

}