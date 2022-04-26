package steps;

import Context.GlobalContext;
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
    private String responseBody;

    @Given("message")
    public void message(String message) {
        GlobalContext.Message = message;
    }

    @When("user hit the endpoint {string}")
    public void iHitTheMirthEndpoint(String endpoint) {
        Response response =
                given().
                        header("Content-Type", "text/plain").
                        body(GlobalContext.Message).
                        post(endpoint);

        //response.prettyPrint();

        apiStatus = response.statusCode();
        responseBody = response.body().asString();
    }

    @Then("user should receive response code {string}")
    public void iShouldReceiveResponseCode(String resStatus) {
        Assert.assertEquals(resStatus, apiStatus.toString());
    }

    @Then("user should receive expected JSON object")
    public void iShouldReceiveJSONObject(String expectedJson) throws JSONException {
        String jsonString = expectedJson.trim();
        //JSONAssert.assertEquals(jsonString, responseBody, JSONCompareMode.LENIENT);
        assertThatJson(responseBody)
                .when(Option.IGNORING_EXTRA_FIELDS)
                .when(Option.IGNORING_ARRAY_ORDER)
                .when(paths("Id", "Scripts[*].BatchId", "Scripts[*].Urgency", "Scripts[*].HOA.Doses[*].AdminDateTime", "Scripts[0].HOA.StartDate", "Scripts[0].HOA.EndDate"), then(Option.IGNORING_VALUES))
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
