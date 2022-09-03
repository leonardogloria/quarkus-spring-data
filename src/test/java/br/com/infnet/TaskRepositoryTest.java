package br.com.infnet;

import br.com.infnet.model.Task;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class TaskRepositoryTest {
    @Test
    void testListAllTasks(){
        given()
                .accept("application/json")
                .when().get("/task")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("description", hasItem("Super Task1"));

        given()
                .accept("application/json")
                .when().get("/task/1")
                .then()
                .statusCode(200)
                .body("description", containsString("Super Task1"));
        given()
                .accept("application/json")
                .when().get("/task/4")
                .then()
                .statusCode(404);
        given()
                .accept("application/json")
                .when().delete("/task/1")
                .then().statusCode(204);
        given()
                .accept("application/json")
                .when().get("/task")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("description", not(containsString("Super Task1")));
        Task task = new Task().builder().description("Super Task 3").developer("Frajola").project("Ghostbusters").build();
        given()
                .accept("application/json")
                .contentType(ContentType.JSON)
                .body("{\"description\": \"Task Ghsot buster\",\"project\": \"Ghost Buster\",\"developer\": \"Patolino\"}")
                .when().post("/task")
                .then().statusCode(204);
    }
}
