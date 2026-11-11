import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static io.restassured.RestAssured.given;
@Tag("UItestdz")
public class dz5Task3TestClass {
    private int good1Id;
    private int good2Id;
    private int good3Id;

    @Step("Создание товара '{name}' с ценой {price} через API")
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
    @Step("Подготовка трех тестовых товаров и открытие главной страницы")
    public void prepareData() {
        good1Id = addgoodapi("good1", 70);
        good2Id = addgoodapi("good2", 100);
        good3Id = addgoodapi("good3", 120);

        Selenide.open("http://localhost:8080");
    }

    @AfterEach
    @Step("Удаление созданных товаров (ID: {this.good1Id}, {this.good2Id}, {this.good3Id}) и закрытие браузера")
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
        Allure.step("Добавление товаров 'good1', 'good2' и 'good3' в корзину", () -> {
            $("button[data-name='good1']").click();
            $("button[data-name='good2']").click();
            $("button[data-name='good3']").click();
        });

        Allure.step("Открытие корзины и оформление заказа", () -> {
            $("#open-cart-btn").click();
            $("#makeOrder").click();
        });

        Allure.step("Проверка уведомления об успешном принятии заказа", () -> {
            $(byText("Заказ принят в обработку!")).shouldBe(visible);
        });
    }

    @Test
    public void checksum() {
        double expectedTotal = Allure.step("Получение цен товаров и добавление их в корзину", () -> {
            double g1 = Double.parseDouble($("button[data-name='good1']").getAttribute("data-price"));
            $("button[data-name='good1']").click();
            double g2 = Double.parseDouble($("button[data-name='good2']").getAttribute("data-price"));
            $("button[data-name='good2']").click();
            double g3 = Double.parseDouble($("button[data-name='good3']").getAttribute("data-price"));
            $("button[data-name='good3']").click();
            return g2 + g3 + g1;
        });

        Allure.step("Открытие корзины и проверка итоговой суммы (" + (int) expectedTotal + ")", () -> {
            $("#open-cart-btn").click();
            $("#total-price").shouldBe(visible).shouldHave(text(String.valueOf((int) expectedTotal)));
        });
    }

    @Test
    public void addasadmin() {
        Allure.step("Переход в панель администратора и авторизация", () -> {
            Selenide.element($("a[href='/admin']")).click();
            Selenide.element($("#username")).sendKeys("admin");
            Selenide.element($("#password")).sendKeys("secret123");
            Selenide.element($(".primary")).click();
        });

        Allure.step("Добавление нового товара 'anothernewgood' с ценой 1337", () -> {
            Selenide.element($("input#n-name")).sendKeys("anothernewgood");
            Selenide.element($("input#n-price")).sendKeys("1337");
            Selenide.element($("#add-btn")).click();
        });

        Allure.step("Проверка всплывающего уведомления об успешном добавлении товара", () -> {
            $("#toast-container > div").shouldHave(text("Товар успешно добавлен"));
        });
    }

    @Test
    public void editgood() {
        Allure.step("Переход в панель администратора и авторизация", () -> {
            Selenide.element($("a[href='/admin']")).click();
            Selenide.element($("#username")).sendKeys("admin");
            Selenide.element($("#password")).sendKeys("secret123");
            Selenide.element($(".primary")).click();
        });

        Allure.step("Переименование товара с ID " + good1Id + " на 'renamedgood'", () -> {
            $("#nm-" + good1Id).setValue("renamedgood");
            $("button.btn-upd[data-id='" + good1Id + "']").click();
        });

        Allure.step("Возврат на главную страницу и проверка нового названия товара", () -> {
            $("body > div.container > div:nth-child(1) > a").click();
            $("#card-" + good1Id).shouldHave(text("renamedgood"));
        });
    }
}
