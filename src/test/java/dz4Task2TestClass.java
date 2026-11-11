import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
@Tag("APItestdz")
public class dz4Task2TestClass {
    private RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .log(LogDetail.ALL)
            .build();

    @Test
    @Step ("Успешное добавление продукта {name} по цене {price} ")
    void Testaddproductsuccess() {
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
            step ("Проверка что пришел код 200", () -> {
                Assertions.assertThat(response.statusCode())
                        .as("Код ответа должен быть 200")
                        .isEqualTo(200);
            }
            );
        }
    }

    @Test
    @Step ("Код 401 при добавлении продукта без авторизации")
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
            step("Проверка, что пришел код 401", () -> {
                Assertions.assertThat(response.statusCode())
                        .as("Код ответа должен быть 401")
                        .isEqualTo(401);
            });
        }
    }

    @Test
    @Step ("Код 403 при добавлении продукта, используя неверный пароль")
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
            step("Проверка что пришел код 403", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 403")
                    .isEqualTo(403);}
            );
        }
    }

    @Test
    @Step ("Код 400 при обращении к некорректному path")
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
            step("Проверка что пришел код 400", () -> {
                Assertions.assertThat(response.statusCode())
                        .as("Код ответа должен быть 400")
                        .isEqualTo(400);
            });
        }
    }

    @Test
    @Step ("Код 400 при добавлении продукта с пустым именем")
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
            step("Проверка что пришел код 400", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 400")
                    .isEqualTo(400);});
            step("Проверка что пришел текст ошибки", () -> {
            Assertions.assertThat(response.jsonPath().getString("name"))
                    .as("Отсутсвует сообщение о пустом имени")
                    .isEqualTo("Name cannot be empty");});

        }
    }

    @Test
    @Step ("Добавление дубликата")
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
            step("Проверка что пришел код 200", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Товар не добавился")
                    .isEqualTo(200);});
            Response response2 = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret123")
                    .post("/goods/add");
            response2.then()
                            .log().all();
            step("Проверка что пришло сообщение о дубликате", () -> {
            Assertions.assertThat(response2.jsonPath().getString("message"))
                    .as("Отсутсвует сообщение о дубликате")
                    .isEqualTo("Good with name '" + name + "' already exists!");});
        }
    }
    @Test
    @Step ("Получение продукта с id {id}")
    void testGetProductSuccess200() {
        {
            Response response = given()
                    .spec(basicRQ)
                    .pathParam("id", 1)
                    .get("/goods/{id}");
            step("Проверка что пришел код 200", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 200")
                    .isEqualTo(200);});
        }
    }

    @Test
    @Step ("Код 404 для продукта с несущестующим id {id}")
    void testGetProductNotFound404() {
        {
            Response response = given()
                    .spec(basicRQ)
                    .pathParam("id", 99999)
                    .get("/goods/{id}");
            step("Проверка что пришел код 404", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 404")
                    .isEqualTo(404);});
        }
    }

    @Test
    @Step ("Код 404 при удалении продукта с несуществующим id {id}")
    void testDeleteProductNotFound404() {
        {
            Response response = given()
                    .spec(basicRQ)
                    .auth().basic("admin", "secret123")
                    .pathParam("id", 99999)
                    .delete("/goods/{id}");
            step("Проверка что пришел код 404", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 404")
                    .isEqualTo(404);});
        }
    }

    @Test
    @Step ("Код 401 при удалении продукта без авторизации")
    void testDeleteProductUnauthorized401() {
        {
            Response response = given()
                    .spec(basicRQ)
                    // Без авторизации
                    .pathParam("id", 2)
                    .delete("/goods/{id}");
            step("Проверка что пришел код 401", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 401")
                    .isEqualTo(401);});
        }
    }

    @Test
    @Step ("Код 403 при удалении продукта с неверными логином и паролем")

    void testDeleteProductForbidden403() {
        {
            Response response = given()
                    .spec(basicRQ)
                    .auth().basic("bad_user", "bad_pass")
                    .pathParam("id", 2)
                    .delete("/goods/{id}");
            step("Проверка что пришел код 403", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 403")
                    .isEqualTo(403);});
        }
    }
    @Test
    @Step ("Изменение названия продукта по id {id} с {name} на {newname}")
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
            step("Проверка что пришел код 200", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 200")
                    .isEqualTo(200);});
        }
    }

    @Test
    @Step ("Код 400 при изменении цены продукта id {id} на {price}")
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
            step("Проверка что пришел код 400", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 400")
                    .isEqualTo(400);});
        }
    }

    @Test
    @Step ("Код 404 при изменении цены продукта с несуществующим id {id}")
    void testPatchProductNotFound404() {
        Map<String, Object> body = Map.of("name", "nonexistent");
        {
            Response response = given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .auth().basic("admin", "secret123")
                    .pathParam("id", 99999)
                    .body(body)
                    .patch("/goods/{id}");
            step("Проверка что пришел код 404", () -> {
            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 404")
                    .isEqualTo(404);});
        }
    }
}

