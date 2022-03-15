import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class ZippoTest {

    @Test
    public void test() {

        given().when().then();

    }


    @Test
    public void checkingStatusCode() {

        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .statusCode(200);

    }

    @Test
    public void loggingRequestDetails() {

        given()
                .log().all()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .statusCode(200);

    }

    @Test
    public void loggingResponseDetails() {

        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void checkingContentType() {

        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);

    }

    @Test
    public void checkCountryTest() {

        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .body("country", equalTo("United States"))
                .statusCode(200);

    }

    @Test
    public void checkCountryAbvTest() {

        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("'country abbreviation'", equalTo("US")); // if there is space in key (first part) you need to cover it with single quote!!!

    }

    @Test
    public void checkStateTest() {

        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places[0].state", equalTo("California"));
    }

}
