package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class labThree {

    private static final String mockServerUrl = "https://35c877f7-45ec-4d30-8eb2-05001f5e9903.mock.pstmn.io/";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = mockServerUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }
    @Test
    public void testGetMockSuccess() {
        given()
                .when()
                .get("ownerName/success")
                .then()
                .statusCode(200)
                .body("name", equalTo("Oleksandra Andriienko"));
    }
    @Test
    public void testGetMockUnsuccess() {
        given()
                .when()
                .get("ownerName/unsuccess")
                .then()
                .statusCode(403)
                .body("exception", equalTo("I won't say my name!"));
    }
    @Test
    public void testPostMock200() {
        given()
                .queryParam("permission", "yes")
                .when()
                .post("/createSomething")
                .then()
                .statusCode(200)
                .body("result", equalTo("'Nothing' was created"));
    }
    @Test
    public void testPostMock400() {
        given()
                .when()
                .post("/createSomething")
                .then()
                .statusCode(400)
                .body("result", equalTo("You don't have permission to create Something"));
    }
    @Test
    public void testPutMock500() {
        Map<String, String> body = Map.of(
                "name", "",
                "surname", ""
        );

        given()
                .body(body)
                .when()
                .put("/updateMe")
                .then()
                .statusCode(500);
    }
    @Test
    public void testDeleteMock() {
        given()
                .header("SessionID", "123456789")
                .when()
                .delete("/deleteWorld")
                .then()
                .statusCode(410) // Gone
                .body("world", equalTo("0"));
    }
}