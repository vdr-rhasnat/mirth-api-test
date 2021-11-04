package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = "src/test/resources/features",
        glue = "steps",
        strict = true,
        plugin = { "com.qmetry.qaf.automation.cucumber.QAFCucumberPlugin", "pretty", "html:target/cucumber-pretty.html", "json:target/cucumber/cucumber.json" }
)
public class TestRunner {
}