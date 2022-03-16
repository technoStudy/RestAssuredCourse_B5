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
    private Object id;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://gorest.co.in";

        reqSpec = given()
                .log().uri()
                .header("Authorization", "Bearer 4a0eee6d56180a8378fa56c1acace9b6c9422edab892bf281ecd37dc9876c019")
                .contentType(ContentType.JSON);


        requestBody = new HashMap<>();
        requestBody.put("name", "TestUser TechnoStudy");
        requestBody.put("email", "testuser@ts.com");
        requestBody.put("gender", "male");
        requestBody.put("status", "active");

    }

    @Test
    public void createUserTest() {

        id = given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .post("/public/v2/users")
                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id");

    }

    @Test(dependsOnMethods = "createUserTest")
    public void createUserNegativeTest() {

        given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .post("/public/v2/users")
                .then()
                .log().body()
                .statusCode(422);

    }

    @Test(dependsOnMethods = "createUserNegativeTest")
    public void editUserTest() {

        String newName = "Updated Name";

        Map<String, String> updatedData = new HashMap<>();
        updatedData.put("name", newName);

        given()
                .spec(reqSpec)
                .body(updatedData)
                .when()
                .put("/public/v2/users/" + id )
                .then()
                .body("name", equalTo(newName))
                .statusCode(200);

    }

    @Test(dependsOnMethods = "editUserTest")
    public void deleteUserTest() {

        given()
                .spec(reqSpec)
                .when()
                .delete("/public/v2/users/" + id)
                .then()
                .log().body()
                .statusCode(204);

    }

}
