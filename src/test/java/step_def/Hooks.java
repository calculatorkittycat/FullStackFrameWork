package step_definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utility.ConfigReader;
import utility.DB_Util;
import utility.Driver;

import java.util.concurrent.TimeUnit;


public class Hooks {


    @Before("@ui")
    public void setupDriver() {


        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Driver.getDriver().get(ConfigReader.read("library_url"));

    }


    @After("@ui")
    public void tearDown(Scenario scenario) {

        // check if scenario failed or not
        if(scenario.isFailed()){
            //this is how we take screenshot in selenium
            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            // scenario.attach(screenshot,"image/png","what ever we want");
            scenario.attach(screenshot,"image/png","Image for failed step");
        }
        Driver.closeBrowser();
    }

    @Before("@db")
    public void dbSetup() {

        DB_Util.createConnection(ConfigReader.read("library2.db.url"),
                ConfigReader.read("library2.db.username"),
                ConfigReader.read("library2.db.password"));

    }


    @After("@db")
    public void dbTearDown() {

        DB_Util.destroy();
    }



}
