package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class MirthApiTestStep {
    private Integer apiStatus;
    private String hl7Message;
    private String responseBody;

    @Given("HL7 message")
    public void hlMessage(String message) {
        hl7Message = message;
    }

    @When("I hit the mirth endpoint {string}")
    public void iHitTheMirthEndpoint(String endpoint) {
        Response response =
                given().
                        header("Content-Type", "text/plain").
                        body(hl7Message).
                        post(endpoint);

        //response.prettyPrint();

        apiStatus = response.statusCode();
        responseBody = response.body().asString();
    }

    @Then("I should receive response code {string}")
    public void iShouldReceiveResponseCode(String resStatus) {
        Assert.assertEquals(resStatus, apiStatus.toString());
    }

    @And("I should receive JSON object")
    public void iShouldReceiveJSONObject(String expectedJson) throws JSONException {
        String jsonString = expectedJson.trim();
        //JSONAssert.assertEquals(jsonString, responseBody, JSONCompareMode.LENIENT);
        assertThatJson(responseBody)
                .whenIgnoringPaths("Id", "Scripts[*].BatchId", "Scripts[*].Urgency", "Scripts[*].HOA.Doses[*].AdminDateTime")
                .isEqualTo(jsonString);
    }
}
