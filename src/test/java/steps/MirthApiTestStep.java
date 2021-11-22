package steps;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.javacrumbs.jsonunit.core.Option;
import org.json.JSONException;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.ConfigurationWhen.paths;
import static net.javacrumbs.jsonunit.core.ConfigurationWhen.then;

public class MirthApiTestStep {
    private Integer apiStatus;
    private String hl7Message;
    private String responseBody;

    @Given("message")
    public void message(String message) {
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
                .when(Option.IGNORING_EXTRA_FIELDS)
                .when(Option.IGNORING_ARRAY_ORDER)
                .when(paths("Id", "Scripts[*].BatchId", "Scripts[*].Urgency", "Scripts[*].HOA.Doses[*].AdminDateTime"), then(Option.IGNORING_VALUES))
                .isEqualTo(jsonString);
    }

    @And("Value of Id should match value of BatchId")
    public void valueOfIdShouldMatchValueOfBatchId() {
        ReadContext ctx = JsonPath.parse(responseBody);
        String Id = ctx.read("$.Id");
        assertThatJson(responseBody)
                .inPath("Scripts[*].BatchId")
                .isArray()
                .containsOnly(Id);
    }
}
