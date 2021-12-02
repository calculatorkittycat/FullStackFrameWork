package utility;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.reset;


public class LibraryTestBase {

    @BeforeAll

    public static void setup(){
        RestAssured.baseURI = "https://library2.cybertekschool.com/rest";
        RestAssured.basePath = "/v1";



    }

    @AfterAll
    public static void teardown(){
        reset();
    }
}