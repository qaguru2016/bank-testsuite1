package ca.qaguru.tests;

import ca.qaguru.lib.TestBase;
import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BankingTests extends TestBase {
    @Test
    public void addAccountTest(){
        Faker faker = new Faker();
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("accountHolderName",faker.name().firstName());
        requestBody.put("balance",faker.number().numberBetween(1000,50000));

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
        System.out.println("Id :" + id);
    }
}
