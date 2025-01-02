package ca.qaguru.tests;

import ca.qaguru.lib.TestBase;
import ca.qaguru.services.AccountService;
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
        AccountService accountService = new AccountService(requestSpecification);
        int id = accountService.addAccount(requestBody);
        System.out.println("Id : "+ id);
    }
    @Test
    public void getAccountById(){
        Faker faker = new Faker();
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("accountHolderName",faker.name().firstName());
        requestBody.put("balance",faker.number().numberBetween(1000,50000));
        AccountService accountService = new AccountService(requestSpecification);
        int id = accountService.addAccount(requestBody);
        System.out.println("Id : "+ id);
        accountService.getAccountById(id,requestBody);
    }
    @Test
    public void depositTest(){
        Faker faker = new Faker();
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("accountHolderName",faker.name().firstName());
        requestBody.put("balance",faker.number().numberBetween(1000,50000));
        AccountService accountService = new AccountService(requestSpecification);
        //Add account
        int id = accountService.addAccount(requestBody);
        System.out.println("Id : "+ id);
        //Deposit
        float depositAmt = 500f;
        accountService.deposit(id,depositAmt);
        //Verify balance
        requestBody.put("balance",((Number)requestBody.get("balance")).floatValue()+depositAmt);
        accountService.getAccountById(id,requestBody);
    }
}
