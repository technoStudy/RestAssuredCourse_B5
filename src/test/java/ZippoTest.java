import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ZippoTest {

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "http://api.zippopotam.us";

    }


    @Test
    public void test() {

        given().when().then();

    }


    @Test
    public void checkingStatusCode() {

        given()
                .when()
                .get("/us/90210")
                .then()
                .statusCode(200);

    }

    @Test
    public void loggingRequestDetails() {

        given()
                .log().all()
                .when()
                .get("/us/90210")
                .then()
                .statusCode(200);

    }

    @Test
    public void loggingResponseDetails() {

        given()
                .when()
                .get("/us/90210")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void checkingContentType() {

        given()
                .when()
                .get("/us/90210")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);

    }

    @Test
    public void checkCountryTest() {

        given()
                .when()
                .get("/us/90210")
                .then()
                .body("country", equalTo("United States"))
                .statusCode(200);

    }

    @Test
    public void checkCountryAbvTest() {

        given()
                .when()
                .get("/us/90210")
                .then()
                .log().body()
                .body("'country abbreviation'", equalTo("US")); // if there is space in key (first part) you need to cover it with single quote!!!

    }

    @Test
    public void checkStateTest() {

        given()
                .when()
                .get("/us/90210")
                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .body("country", equalTo("United States"))
                .statusCode(200);
    }

    @Test
    public void pathParametersTest() {

        String country = "us";
        String zipcode = "90210";

        given()
                .pathParam("country", country)
                .pathParam("zipcode", zipcode)
                .when()
                .get("/{country}/{zipcode}")
                .then()
                .statusCode(200);

    }

    @Test
    public void queryParameters() {

        String gender = "female";
        String status = "active";

        given()
                .param("gender", gender)
                .param("status", status)
                .when()
                .get("https://gorest.co.in/public/v1/users")
                .then()
                .log().body();

    }


    @Test
    public void extractValueTest() {

        Object countryInfo = given()
                .when()
                .get("/us/90210")
                .then()
                .extract().path("country");

        System.out.println(countryInfo);

    }

}
