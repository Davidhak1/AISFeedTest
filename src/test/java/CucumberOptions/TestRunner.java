package CucumberOptions;

import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.aeonbits.owner.ConfigFactory;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import resources.Environment;

import java.io.File;

import static resources.base.testEnvironment;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/java/features",
		glue="stepDefinitions",
//        tags = {"@DB_Download-validation"},
        tags = {"@AIS"},
//		  tags = {"@DB-Response-validation"
        plugin={"com.cucumber.listener.ExtentCucumberFormatter:target/html/report.html"},
        strict = false,
        dryRun = false
)

public class TestRunner extends AbstractTestNGCucumberTests  {
    @Parameters({"environment"})
    @BeforeClass
    public void setup(String environment) throws Exception {
        ConfigFactory.setProperty("env", environment);
        testEnvironment = ConfigFactory.create(Environment.class);
        System.out.println("AIS BASE PATH ----->>>>>>>> " + testEnvironment.ais_base_path());
    }

    @AfterClass
	public static void setup() {
		Reporter.loadXMLConfig(new File("src/main/java/resources/extent-config.xml"));
		Reporter.setSystemInfo("Test User", "Davit Hakobyan");
		Reporter.setSystemInfo("Operating System Type", System.getProperty("os.name"));
        Reporter.setSystemInfo("OS version", System.getProperty("os.version"));
        Reporter.setSystemInfo("Web App Name", "AIS Incentives Feed");
		Reporter.setSystemInfo("Build version", "v 1.3");
		Reporter.setTestRunnerOutput("Cucumber reporting using Extent Config");
	}
}
