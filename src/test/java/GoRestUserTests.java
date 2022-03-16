import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUserTests {

    private RequestSpecification reqSpec;
    private Map<String, String> requestBody;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://gorest.co.in";

        reqSpec = given()
                .log().uri()
                .header("Authorization", "Bearer 4a0eee6d56180a8378fa56c1acace9b6c9422edab892bf281ecd37dc9876c019")
                .contentType(ContentType.JSON);


        requestBody = new HashMap<>();
        requestBody.put("name", "TestUser from TS");
        requestBody.put("email", "testuser@technostudy.com");
        requestBody.put("gender", "male");
        requestBody.put("status", "active");

    }

    @Test
    public void createUserTest() {

        given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .post("/public/v2/users")
                .then()
                .statusCode(201);

    }

}
