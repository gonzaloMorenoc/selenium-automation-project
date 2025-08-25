package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepDefinitions"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/smoke-html-report",
        "json:target/cucumber-reports/smoke-report.json",
        "junit:target/cucumber-reports/smoke-junit-report.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    tags = "@smoke"
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}