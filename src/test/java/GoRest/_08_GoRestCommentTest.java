package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class _08_GoRestCommentTest {
    //     {
    //          "id": 95069,
    //          "post_id": 122490,
    //          "name": "Malti Kaur",
    //          "email": "kaur_malti@strosin-keebler.example",
    //          "body": "In ut vel."
    //     }


    Faker randomUreteci=new Faker();
    RequestSpecification reqSpec;
    int commentID=0;


    @BeforeClass
    public void Setup()
    {
        baseURI="https://gorest.co.in/public/v2/comments";

        reqSpec= new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer eb134d5bcbdf9d5d073d66aa35b8d60297f3c6a33b2039d4c2a0acac6d4b1ac7")
                .setContentType(ContentType.JSON)
                .build();
    }
    // Soru : CreateComment testini yapiniz

    @Test
    public void CreateComment(){
        String fullName=randomUreteci.name().fullName();
        String email=randomUreteci.internet().emailAddress();
        String body=randomUreteci.lorem().paragraph();
        String postId="122490";

        Map<String,String> newComment=new HashMap<>();
        newComment.put("name",fullName);
        newComment.put("email",email);
        newComment.put("body",body);
        newComment.put("post_id",postId);

        commentID=
                given()
                        .spec(reqSpec)
                        .body(newComment)

                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;

        System.out.println("commentID = " + commentID);
    }
}
