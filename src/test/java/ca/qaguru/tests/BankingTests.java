package ca.qaguru.tests;

import ca.qaguru.lib.TestBase;
import ca.qaguru.services.AccountService;
import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        accountService.getAccountById(id, HttpStatus.SC_OK, requestBody);
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
        accountService.getAccountById(id, HttpStatus.SC_OK, requestBody);
    }
    @Test
    public void withdrawTest(){
        Faker faker = new Faker();
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("accountHolderName",faker.name().firstName());
        requestBody.put("balance",faker.number().numberBetween(1000,50000));
        AccountService accountService = new AccountService(requestSpecification);
        //Add account
        int id = accountService.addAccount(requestBody);
        System.out.println("Id : "+ id);
        //Deposit
        float withdrawalAmt = 500f;
        accountService.withdraw(id,withdrawalAmt);
        //Verify balance
        requestBody.put("balance",((Number)requestBody.get("balance")).floatValue()-withdrawalAmt);
        accountService.getAccountById(id, HttpStatus.SC_OK, requestBody);
    }

    @Test
    public void deleteAccountById(){
        Faker faker = new Faker();
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("accountHolderName",faker.name().firstName());
        requestBody.put("balance",faker.number().numberBetween(1000,50000));
        AccountService accountService = new AccountService(requestSpecification);
        //Add Account
        int id = accountService.addAccount(requestBody);
        //Delete the account
        accountService.deleteAccount(id);
        //Get the account by id
        accountService.getAccountById(id,HttpStatus.SC_INTERNAL_SERVER_ERROR,null);
    }
    @Test
    public void getAllAccounts(){
        Faker faker = new Faker();
        int count = 3;
        List<Map<String,Object>> requestBodies = new ArrayList<>();
        AccountService accountService = new AccountService(requestSpecification);
        for(int i =0; i<3;i++){
            Map<String,Object> requestBody = new HashMap<>();
            requestBody.put("accountHolderName",faker.name().firstName());
            requestBody.put("balance",faker.number().randomDouble(2,1000,50000));
            int id = accountService.addAccount(requestBody);
            requestBody.put("id", id);
            requestBodies.add(requestBody);
        }
        accountService.getAllAccounts(requestBodies);
    }
}
