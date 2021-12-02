package step_def;

import io.cucumber.java.en.*;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pom.Homepage;
import pom.LoginPage;
import utility.ConfigReader;
import utility.DB_Util;
import utility.LibraryTestBase;

import java.util.ArrayList;
import java.util.List;

public class TestStepDef extends LibraryTestBase {

    List<Integer> fromUi = new ArrayList<>();
    List<Integer> fromDb = new ArrayList<>();
    List<Integer> fromApi = new ArrayList<>();

    @Given("establish the database connection")
    public void establish_the_database_connection() {
        DB_Util.createConnection(ConfigReader.read("library2.db.url"),
                ConfigReader.read("library2.db.username"),
                ConfigReader.read("library2.db.password"));
    }

    @When("I check the homepage and get numbers")
    public void i_check_the_homepage_and_get_numbers() {
        new LoginPage().goTo();
        new LoginPage().login(ConfigReader.read("librarian"), ConfigReader.read("password"));

        fromUi = new Homepage().getNumsFromUi();
        System.out.println(fromUi);

    }

    @When("I check the database for the numbers")
    public void i_check_the_database_for_the_numbers() {

        DB_Util.runQuery("select count(*) from books");
        int totalBooksFromDataBase = Integer.parseInt(DB_Util.getFirstRowFirstColumn());
        fromDb.add(totalBooksFromDataBase);

        DB_Util.runQuery("select count(*) from users");
        int usersFromDataBase = Integer.parseInt(DB_Util.getFirstRowFirstColumn());
        fromDb.add(usersFromDataBase);

        DB_Util.runQuery("select count(*) from book_borrow where is_returned=0");
        int borrowedBooksFromDatabase = Integer.parseInt(DB_Util.getFirstRowFirstColumn());
        fromDb.add(borrowedBooksFromDatabase);

        System.out.println(fromDb);

        DB_Util.destroy();


    }

    //@ParameterizedTest
    //@ValueSource(strings = "book_count")
    @When("I check the API for the numbers")
    public void i_check_the_api_for_the_numbers(String bCount) {
        baseURI = "https://library2.cybertekschool.com";
        basePath = "/rest/v1";

        //getting token
        Response response = given()
                .contentType(ContentType.URLENC)
                .formParam("email", ConfigReader.read("librarian"))
                .formParam("password", ConfigReader.read("password")).
                when()
                .post("/login");

        String token = response.path("token");

        //getting numbers
        Response response1 = given()
                //.log().all()
                .header("x-library-token", token)
                .when()
                .get("/dashboard_stats");


        JsonPath jp = response1.jsonPath();


        int a = Integer.parseInt(jp.getString("book_count"));
        int c = Integer.parseInt(jp.getString("users"));
        int b = Integer.parseInt(jp.getString("borrowed_books"));

        fromApi.add(a);
        fromApi.add(c);
        fromApi.add(b);


        System.out.println(fromApi);


        reset();

    }

    @Then("All the number sets should match")
    public void all_the_number_sets_should_match() {

        Boolean b = false;

        if (fromDb.equals(fromApi) && fromDb.equals(fromUi)) {
            System.out.println("ALL ARE MATCH");
            System.out.println(fromUi);
            System.out.println(fromDb);
            System.out.println(fromApi);
            b = false;
        } else {
            System.out.println("NO MATCH");
            System.out.println(fromUi);
            System.out.println(fromDb);
            System.out.println(fromApi);
            b = true;
        }

        Assertions.assertTrue(b);
    }

}
