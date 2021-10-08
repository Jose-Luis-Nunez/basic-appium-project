package appium.utils

import io.restassured.RestAssured
import io.restassured.http.ContentType

private const val ANY_URL = "http://127.0.0.1:4723/wd/hub"

class RestAssuredClient {
    companion object {
        fun postExample(payload: String) {
            RestAssured.given()
                    .auth().none()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON)
                    .`when`()
                    .body(payload)
                    .post(ANY_URL)
                    .then()
                    .statusCode(204)
        }

        fun getExample() {
            RestAssured.given()
                    .auth().none()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON)
                    .`when`()
                    .get(ANY_URL)
                    .then()
                    .statusCode(204)
        }

        const val expectedPayload = """{
          "data": {
            "events": [],
            "renew": false,
            "remainingDays": 0,
            "status": "INACTIVE",
            "trialMonth": true,
            "estimatedCycleEnd": "2020-11-12T13:52:55.570Z",
            "calculationDateTime": "2020-10-12T13:52:55.570Z",
            "billingCycle": 30
          }
        }"""
    }
}
