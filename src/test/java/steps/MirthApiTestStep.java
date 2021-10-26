package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static io.restassured.RestAssured.given;

public class MirthApiTestStep {
    private int apiStatus;
    private String hl7Message;
    private String responseBody;

    @Given("HL7 message")
    public void hlMessage(String message) {
        hl7Message = message;
    }

    @When("I hit the endpoint {string}")
    public void iHitTheEndpoint(String endpoint) {
        Response response =
                given().
                header("Content-Type", "text/plain").
                body(hl7Message).
                post(endpoint);

        //response.prettyPrint();

        apiStatus = response.statusCode();
        responseBody = response.body().asString();
    }

    @Then("I should receive status code {int}")
    public void iShouldReceiveStatusCode(int resStatus) {
        Assert.assertEquals(resStatus, apiStatus);
    }

    @And("I should receive JSON object")
    public void iShouldReceiveJSONObject(String expectedJson) throws JSONException {
        String jsonString = expectedJson.trim();
        JSONAssert.assertEquals(jsonString, responseBody, JSONCompareMode.LENIENT);
    }
}
