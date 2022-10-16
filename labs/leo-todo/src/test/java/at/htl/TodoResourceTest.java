package at.htl;

import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TodoResourceTest {

    public static final String PREPARE_TALK_FOR_SCHILF_HTL_LEONDING = "Prepare talk for SCHILF @ HTL Leonding";
    long todoId;

    @BeforeEach
    @Transactional
    public void setup() {
        Todo.deleteAll();
        Todo t = new Todo();
        t.title = PREPARE_TALK_FOR_SCHILF_HTL_LEONDING;
        t.order = 1;
        t.persistAndFlush();
        todoId = t.id;
        System.err.println("===========>"+todoId);
    }

    @Test
    public void testList() {
        given()
          .when().get("/api")
          .then()
             .statusCode(200)
             .body("[0].title",is(PREPARE_TALK_FOR_SCHILF_HTL_LEONDING));
    }

    @Test
    public void testAdd() {
        given()
                .body(Map.of("title", "Added", "order", "2"))
                .contentType(ContentType.JSON)
                .when().post("/api")
                .then()
                .statusCode(200)
                .body("title", is("Added"));

        given()
                .when().get("/api")
                .then()
                .statusCode(200)
                .body("[0].title",is(PREPARE_TALK_FOR_SCHILF_HTL_LEONDING))
                .body("[1].title",is("Added"));
    }

    @Test
    public void testEdit() {
        given()
                .body(Map.of("title", "Edited"))
                .contentType(ContentType.JSON)
          .when().patch("/api/" + todoId)
          .then()
                .statusCode(200)
                .body("title", is("Edited"));

        given()
                .when().get("/api")
                .then()
                .statusCode(200)
                .body("[0].title",is("Edited"));
    }

    @Test
    public void testDelete() {
        given()
                .when().delete("/api/" + todoId)
                .then()
                .statusCode(204);

        given()
                .when().get("/api")
                .then()
                .statusCode(200)
                .body("size()",is(0));
    }

}