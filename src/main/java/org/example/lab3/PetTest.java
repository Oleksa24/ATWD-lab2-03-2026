package org.example.lab3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetTest {
    private static final String baseUrl = "https://petstore.swagger.io";
    private static final String PET = "/pet",
            PET_ID = PET + "/{petId}";

    private long myPetId = 121*23*1;
    private String myPetName = "AndriienkoOleksandra";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = "/v2";
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyCreatePetAction() {
        Map<String, Object> petBody = Map.of(
                "id", myPetId,
                "name", myPetName,
                "status", "available"
        );
        given()
                .body(petBody)
                .when()
                .post(PET)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyCreatePetAction")
    public void verifyGetPetAction() {
        given()
                .pathParam("petId", myPetId)
                .when()
                .get(PET_ID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", equalTo(myPetName));
    }

    @Test(dependsOnMethods = "verifyGetPetAction")
    public void verifyUpdatePetAction() {
        Map<String, Object> updatedPetBody = Map.of(
                "id", myPetId,
                "name", myPetName + " Updated",
                "status", "sold"
        );
        given()
                .body(updatedPetBody)
                .when()
                .put(PET)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}