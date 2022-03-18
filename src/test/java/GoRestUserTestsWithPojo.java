import Pojo.GoRestUser;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUserTestsWithPojo {

    private RequestSpecification reqSpec;
    private GoRestUser user;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://gorest.co.in";

        reqSpec = given()
                .log().uri()
                .header("Authorization", "Bearer 4a0eee6d56180a8378fa56c1acace9b6c9422edab892bf281ecd37dc9876c019")
                .contentType(ContentType.JSON);

        user = new GoRestUser();
        user.setName("TestUser by TS");
        user.setEmail("tsuser121133@techno.study");
        user.setGender("female");
        user.setStatus("active");

    }

    @Test
    public void createUserTest() {

        user.setId(
                given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post("/public/v2/users")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")
        );

    }

    @Test(dependsOnMethods = "createUserTest")
    public void getUserTest() {

        given()
                .spec(reqSpec)
                .when()
                .get("/public/v2/users/" + user.getId())
                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()));

    }

    @Test(dependsOnMethods = "getUserTest")
    public void createUserNegativeTest() {

        given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post("/public/v2/users")
                .then()
                .log().body()
                .statusCode(422);

    }

    @Test(dependsOnMethods = "createUserNegativeTest")
    public void editUserTest() {

        String newName = "Updated Name by TS";

        Map<String, String> editUserBody = new HashMap<>();
        editUserBody.put("name", newName);

        given()
                .spec(reqSpec)
                .body(editUserBody)
                .when()
                .put("/public/v2/users/" + user.getId())
                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(newName));

    }

}
