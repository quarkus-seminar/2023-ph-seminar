package at.htl.elytronfile.boundary;

import at.htl.elytronfile.entity.Vehicle;
import at.htl.elytronfile.entity.dto.VehicleDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.internal.http.Status;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
class VehicleEndpointTest {

    static VehicleDto horch = new VehicleDto("Horch", "P 240");
    static VehicleDto tucker = new VehicleDto("Tucker", "'48");
    static VehicleDto bmw = new VehicleDto("BMW", "Isetta");

    @BeforeAll
    static void beforeAll() {
    }

    @Order(100)
    @Test
    void allVehiclesWithoutAuthentication() {

        Response response = given().get("/api");

        //assertThat(response.statusCode()).isEqualTo(javax.ws.rs.core.Response.Status.UNAUTHORIZED.getStatusCode());

        // 401 ... unauthorized
        assertThat(response.statusCode()).isEqualTo(401);

    }

    @Order(200)
    @Test
    void allVehiclesWithAuthentication() {

        // arrange

        // act
        Response response = given()
                .auth().basic("hansi", "passme")
                .get("/api");

        // assert
        //assertThat(response.statusCode()).isEqualTo(javax.ws.rs.core.Response.Status.UNAUTHORIZED.getStatusCode());
        // 401 ... unauthorized
        assertThat(response.statusCode()).isEqualTo(200);

        String body = response.body().asPrettyString();
        System.out.println(body);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<VehicleDto> vehicles = jsonPathEvaluator.getList("", VehicleDto.class);
        assertThat(vehicles.size()).isEqualTo(3);
        assertThat(vehicles).contains(horch,bmw,tucker);
    }

}