package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class MirthApiTestStep {
    private int apiStatus;
    private String hl7Message;

    @Given("HL7 message")
    public void hlMessage(String docString) {
        hl7Message = docString;
    }

    @When("I hit the endpoint {string}")
    public void iHitTheEndpoint(String endpoint) {
        Response response =
                given().
                header("Content-Type", "text/plain").
                body(hl7Message).
                post(endpoint);

        response.prettyPrint();

        apiStatus = response.statusCode();
    }

    @Then("I should receive status code {int}")
    public void iShouldReceiveStatusCode(int resStatus) {
        Assert.assertEquals(resStatus, apiStatus);
    }
}
