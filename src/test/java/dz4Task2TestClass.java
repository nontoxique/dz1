import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
@Tag("APItestdz")
public class dz4Task2TestClass {
    private RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .log(LogDetail.ALL)
            .build();

    @Test
    void Testaddproductsucces() {
        String name = "kekus7";
        double price = 7000;
        Map<String, Object> body = Map.of(
                "name", name,
                "price", price
        );
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret123")
                    .post("/goods/add");
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 200")
                    .isEqualTo(200);
        }
    }

    @Test
    void testaddproduct401() {
        String name = "kekus7";
        double price = 7000;
        Map<String, Object> body = Map.of(
                "name", name,
                "price", price
        );
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post("/goods/add");
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 401")
                    .isEqualTo(401);
        }
    }

    @Test
    void Testaddproduct403() {
        String name = "kekus7";
        double price = 7000;
        Map<String, Object> body = Map.of(
                "name", name,
                "price", price
        );
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret12")
                    .post("/goods/add");
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 403")
                    .isEqualTo(403);
        }
    }

    @Test
    void Testaddproduct400() {
        String name = "kekus7";
        double price = 7000;
        Map<String, Object> body = Map.of(
                "name", name,
                "price", price
        );
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret123")
                    .post("/goods/ad");
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 400")
                    .isEqualTo(400);
        }
    }

    @Test
    void Testaddproductemptyname() {
        String name = "kekus7";
        double price = 7000;
        Map<String, Object> body = Map.of(
                "price", price
        );
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret123")
                    .post("/goods/add");
            response.then()
                            .log().all();
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 400")
                    .isEqualTo(400);
            Assertions.assertThat(response.jsonPath().getString("name"))
                    .as("Отсутсвует сообщение о пустом имени")
                    .isEqualTo("Name cannot be empty");

        }
    }

    @Test
    void Testaddproductdublicate() {
        String name = "kekus10";
        double price = 7000;
        Map<String, Object> body = Map.of(
                "name", name,
                "price", price
        );
        {
           Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret123")
                    .post("/goods/add");
            response.then()
                    .log().all();
            Assertions.assertThat(response.statusCode())
                    .as("Товар не добавился")
                    .isEqualTo(200);
            Response response2 = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret123")
                    .post("/goods/add");
            response2.then()
                            .log().all();
            Assertions.assertThat(response2.jsonPath().getString("message"))
                    .as("Отсутсвует сообщение о дубликате")
                    .isEqualTo("Good with name '" + name + "' already exists!");
        }
    }
    @Test
    void testGetProductSuccess200() {
        {
            Response response = given()
                    .spec(basicRQ)
                    .pathParam("id", 1)
                    .get("/goods/{id}");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 200")
                    .isEqualTo(200);
        }
    }

    @Test
    void testGetProductNotFound404() {
        {
            Response response = given()
                    .spec(basicRQ)
                    .pathParam("id", 99999)
                    .get("/goods/{id}");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 404")
                    .isEqualTo(404);
        }
    }

    @Test
    void testDeleteProductNotFound404() {
        {
            Response response = given()
                    .spec(basicRQ)
                    .auth().basic("admin", "secret123")
                    .pathParam("id", 99999)
                    .delete("/goods/{id}");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 404")
                    .isEqualTo(404);
        }
    }

    @Test
    void testDeleteProductUnauthorized401() {
        {
            Response response = given()
                    .spec(basicRQ)
                    // Без авторизации
                    .pathParam("id", 2)
                    .delete("/goods/{id}");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 401")
                    .isEqualTo(401);
        }
    }

    @Test
    void testDeleteProductForbidden403() {
        {
            Response response = given()
                    .spec(basicRQ)
                    .auth().basic("bad_user", "bad_pass")
                    .pathParam("id", 2)
                    .delete("/goods/{id}");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 403")
                    .isEqualTo(403);
        }
    }
    @Test
    void testPatchProductSuccess200() {
        Map<String, Object> body = Map.of("name", "newname");
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .auth().basic("admin", "secret123")
                    .pathParam("id", 1)
                    .body(body)
                    .patch("/goods/{id}");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 200")
                    .isEqualTo(200);
        }
    }

    @Test
    void testPatchProductBadRequest400() {
        Map<String, Object> body = Map.of("price", -10.5);
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .auth().basic("admin", "secret123")
                    .pathParam("id", 1)
                    .body(body)
                    .patch("/goods/{id}");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 400")
                    .isEqualTo(400);
        }
    }

    @Test
    void testPatchProductNotFound404() {
        Map<String, Object> body = Map.of("name", "nonexistant");
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .auth().basic("admin", "secret123")
                    .pathParam("id", 99999)
                    .body(body)
                    .patch("/goods/{id}");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 404")
                    .isEqualTo(404);
        }
    }
}

