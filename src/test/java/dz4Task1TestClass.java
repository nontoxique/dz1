import io.qameta.allure.Allure;
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
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;

@Tag("APItestdz")
public class dz4Task1TestClass {

    private RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .log(LogDetail.ALL)
            .build();

    @Test
    void Testempty200() {
        step("Получить список товаров (без спецификации, page=0, size=10) и проверить, что он пустой", () -> {
            given()
                    .baseUri("http://localhost:8080")
                    .queryParam("page", 0)
                    .queryParam("size", 10)
                    .when()
                    .get("/goods/list")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("goods", empty());
        });
    }

    @Test
    void Testempty200spec() {
        step("Получить список товаров (со спецификацией, page=1, size=10) и проверить, что он пустой", () -> {
            given()
                    .spec(basicRQ)
                    .queryParam("size", 10)
                    .queryParam("page", 1)
                    .get("/goods/list")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("goods", empty());
        });
    }

    @Test
    void Testaddandget() {
        String name = "kekus7";
        double price = 7000;
        Map<String, Object> body = Map.of(
                "name", name,
                "price", price
        );

        step("Добавить товар '" + name + "' с ценой " + price + " и проверить статус 200", () -> {
            given()
                    .spec(basicRQ)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret123")
                    .post("/goods/add")
                    .then()
                    .log().all()
                    .statusCode(200);
        });

        step("Проверить наличие товара '" + name + "' с ценой " + price + " в списке", () -> {
            given()
                    .spec(basicRQ)
                    .queryParam("size", 10)
                    .queryParam("page", 0)
                    .get("/goods/list")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("goods.name", hasItems(name))
                    .body("goods.price", hasItems(price));
        });
    }

    @Test
    void Testaddandgetassertj() {
        String name = "kekus8";
        double price = 7000;
        Map<String, Object> body = Map.of(
                "name", name,
                "price", price
        );

        Response response1 = step("Отправить запрос на добавление товара '" + name + "'", () ->
                given()
                        .spec(basicRQ)
                        .contentType(ContentType.JSON)
                        .body(body)
                        .auth()
                        .basic("admin", "secret123")
                        .post("/goods/add")
        );

        step("Проверить, что код ответа при добавлении равен 200", () -> {
            Assertions.assertThat(response1.statusCode())
                    .as("Код ответа должен быть 200")
                    .isEqualTo(200);
        });

        Response response2 = step("Отправить запрос на получение списка товаров", () ->
                given()
                        .spec(basicRQ)
                        .queryParam("size", 10)
                        .queryParam("page", 0)
                        .get("/goods/list")
        );

        step("Проверить статус 200 и наличие товара '" + name + "' в ответе", () -> {
            Assertions.assertThat(response2.statusCode())
                    .as("Код ответа должен быть 200")
                    .isEqualTo(200);

            Assertions.assertThat(response2.jsonPath().getString("goods.name"))
                    .as("В ответе должен содержаться " + name)
                    .contains(name);
        });
    }
}
