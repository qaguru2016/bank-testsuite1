package ca.qaguru.services;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountService {
    private RequestSpecification requestSpecification;

    public AccountService(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public int addAccount(Map<String,Object> requestBody){
        ValidatableResponse validatableResponse =
                given()
                        .spec(requestSpecification)
                        .body(requestBody)
                        .when()
                        .post()
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(HttpStatus.SC_CREATED);
        int id = validatableResponse.extract().jsonPath().getInt("id");
        return id;
    }

    public void getAccountById(int id, Map<String, Object> requestBody) {
        given()
                .spec(requestSpecification)
                .when()
                .get("/"+id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id",equalTo(id))
                .body("accountHolderName",is(requestBody.get("accountHolderName")))
                .body("balance", is(((Integer)requestBody.get("balance")).floatValue()));
    }
}
