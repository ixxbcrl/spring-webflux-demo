import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TestSimulation extends Simulation {
  val URI = "/observe"

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json;charset=UTF-8")

  val httpProtocolBlocking = http
    .baseUrl("http://localhost:8082")
    .acceptHeader("application/json;charset=UTF-8")

  val scn1 = scenario("TestSimulation")
    .exec(
      repeat(10) {
        exec(
          http("reactive")
            .get(URI).queryParam("delay", 20).check(status.is(200)))
          .pause(5)
      }
    )


  val scn2 = scenario("LoadSimulation")
    .exec(
      repeat(10) {
        exec(
          http("blocking")
            .get(URI).queryParam("delay", 20).check(status.is(200)))
          .pause(5)
      }
  )

  setUp(
    scn1.inject(atOnceUsers(1000))
      .protocols(httpProtocol),
    scn2.inject(atOnceUsers(1000))
      .protocols(httpProtocolBlocking)
  )

}