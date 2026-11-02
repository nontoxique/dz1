package dz8;
import com.codeborne.selenide.Selenide;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dz8.pages.adminPage;
import dz8.pages.mainPage;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

    public class dz8TestClassTask2 {
        private int good1Id;
        private int good2Id;
        private int good3Id;
        private mainPage mainPage;

        private int addgoodapi(String name, double price) {
            Map<String, Object> body = Map.of(
                    "name", name,
                    "price", price
            );
            Response response = given()
                    .baseUri("http://localhost:8080")
                    .contentType(ContentType.JSON)
                    .body(body)
                    .auth()
                    .basic("admin", "secret123")
                    .post("/goods/add");

            Assertions.assertThat(response.statusCode())
                    .as("Код ответа должен быть 200")
                    .isEqualTo(200);

            return response.path("data.id");
        }

        @BeforeEach
        public void prepareData() {
            good1Id = addgoodapi("good1", 70);
            good2Id = addgoodapi("good2", 100);
            good3Id = addgoodapi("good3", 120);

            mainPage = Selenide.open("http://localhost:8080", mainPage.class);
        }

        @AfterEach
        void deleteandclose() {
            List.of(good1Id, good2Id, good3Id).forEach(id -> {
                given()
                        .baseUri("http://localhost:8080")
                        .auth().basic("admin", "secret123")
                        .delete("/goods/" + id);
            });
            Selenide.closeWebDriver();
        }

        @Test
        public void add3goods() {
            mainPage.clickAddButton("good1")
                    .clickAddButton("good2")
                    .clickAddButton("good3")
                    .openCart()
                    .makeOrder()
                    .checkOrderProcessed();
        }

        @Test
        public void checksum() {
            double g1 = mainPage.getGoodPrice("good1");
            mainPage.clickAddButton("good1");

            double g2 = mainPage.getGoodPrice("good2");
            mainPage.clickAddButton("good2");

            double g3 = mainPage.getGoodPrice("good3");
            mainPage.clickAddButton("good3");

            int expectedTotal = (int) (g1 + g2 + g3);

            mainPage.openCart()
                    .checkTotalPrice(expectedTotal);
        }

        @Test
        public void addasadmin() {
            adminPage adminPage = mainPage.clickAdminLink()
                    .loginAs("admin", "secret123");

            adminPage.addNewGood("anothernewgood", "1337")
                    .checkToastText("Товар успешно добавлен!");
        }

        @Test
        public void editgood() {
            adminPage adminPage = mainPage.clickAdminLink()
                    .loginAs("admin", "secret123");

            adminPage.editGoodName(good1Id, "renamedgood")
                    .clickBackToMain()
                    .checkGoodCardText(good1Id, "renamedgood");
        }
    }