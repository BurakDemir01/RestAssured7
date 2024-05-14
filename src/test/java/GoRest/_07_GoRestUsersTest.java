package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;

public class _07_GoRestUsersTest {

    RequestSpecification reqSpec;
    Faker randomUreteci = new Faker();
    int userID = 0;

    // Token ı aldım ,
    // usersCreate için neler lazım, body(user bilgileri)
    // enpoint i aldım gidiş metodu
    // BeforeClass ın içinde yapılacaklar var mı? nelerdir ?  url set ve spec hazırlanmalı

    @BeforeClass
    public void Setup() {
        baseURI = "https://gorest.co.in/public/v2/users";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer eb134d5bcbdf9d5d073d66aa35b8d60297f3c6a33b2039d4c2a0acac6d4b1ac7")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void CreatUser() {
        // Create User Testini yapınız
        String rndFullName = randomUreteci.name().fullName();
        String rndEmail = randomUreteci.internet().emailAddress();

        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", rndFullName);
        newUser.put("gender", "male");
        newUser.put("email", rndEmail);
        newUser.put("status", "active");

        userID =
                given()
                        .spec(reqSpec)
                        .body(newUser)

                        .when()
                        .post("")// http ile başlamıyorsa BASEURI geçerli

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "CreatUser")
    public void getUserById(){
        given()
                .spec(reqSpec)

                .when()
                //.get("/"+userID)
                .get("https://gorest.co.in/public/v2/users/"+userID)

                .then()
                //.log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
                ;
    }

    @Test(dependsOnMethods = "getUserById")
    public void UpdateUser(){
        String updName="Alex Kartal";

        Map<String,String> updUser=new HashMap<>();
        updUser.put("name",updName);

        given()
                .spec(reqSpec)
                .body(updUser)

                .when()
                .put("/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
                .body("name", equalTo(updName))
                // .extract().response().getStatusCode()  // bu sekil status kodu alip kontrol edilebilir
        ;
    }

    @Test(dependsOnMethods = "UpdateUser")
    public void deleteUser(){
        given()
                .spec(reqSpec)

                .when()
                .delete("/"+userID)

                .then()
                // .log().body()
                .statusCode(204);
    }

    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative(){
        given()
                .spec(reqSpec)

                .when()
                .delete("/"+userID)

                .then()
                .statusCode(404);
    }
}


