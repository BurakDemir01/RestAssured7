import Model.Location;
import Model.ToDo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class _04_Tasks {


    @Test
    public void task1() {
        /**
         * Task 1
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * expect status 200
         * expect content type JSON
         * expect title in response body to be "quis ut nam facilis et officia qui"
         */

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                //.log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;
    }

    @Test
    public void task2() {
        /**
         Task 2
         create a request to https://jsonplaceholder.typicode.com/todos/2
         expect status 200
         expect content type JSON
         *a) expect response completed status to be false(hamcrest)
         *b) extract completed field and testNG assertion(testNG)
         */
//a
        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false)) //hemcrest ile assertion
        ;

//b
        boolean completedData =
                given()

                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().path("completed");

        Assert.assertFalse(completedData);
    }

    @Test
    public void task3() {
        /** Task 3
         create a request to https://jsonplaceholder.typicode.com/todos/2
         expect status 200
         Converting Into POJO
         */

        ToDo todoNesne =
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .statusCode(200)
                        //.log().body()
                        .extract().body().as(ToDo.class);
        ;

        System.out.println("todoNesne = " + todoNesne);
        System.out.println("todoNesne.getTitle() = " + todoNesne.getTitle());
        System.out.println("todoNesne.getId() = " + todoNesne.getId());
        System.out.println("todoNesne.isCompleted() = " + todoNesne.isCompleted());
    }
}
