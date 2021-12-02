package runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "src/test/step_def",
        features = "@target/rerun.txt"
)

public class FailedTestRunner {

}
