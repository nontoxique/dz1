import com.codeborne.selenide.Selenide;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class dz5Task2TestClass {
    private int good1Id;

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
        Selenide.open("http://localhost:8080");
    }

    @AfterEach
    void deleteAndClose() {
        given()
                .baseUri("http://localhost:8080")
                .auth().basic("admin", "secret123")
                .delete("/goods/" + good1Id);

        Selenide.closeWebDriver();
    }

    @Test
    void addgoodadmin() {
        Selenide.element($("a[href='/admin']")).click();
        Selenide.element($("#username")).sendKeys("admin");
        Selenide.element($("#password")).sendKeys("secret123");
        Selenide.element($(".primary")).click();
        Selenide.element($("input#n-name")).sendKeys("newgood");
        Selenide.element($("input#n-price")).sendKeys("1337");
        Selenide.element($("#add-btn")).click();
        Selenide.element($("div:nth-child(1) > a")).click();
        $("[data-name='newgood']:not([class='btn'])")
                .as("Новый товар не появился")
                .shouldBe(visible);
    }

    @Test
    void addtocart() {
        String id = Selenide.element($("button[data-name=\'newgood\']")).getAttribute("data-id");
        Selenide.element($("button[data-name=\"newgood\"]")).click();
        Selenide.element($("#open-cart-btn")).click();

        Selenide.element($("#cart-item-" + id + "> div:nth-child(1) > b"))
                .as("Товар не появился в корзине")
                .shouldHave(text("newgood"));
    }

    @Test
    void wrongcreds() {
        $("a[href='/admin']").click();
        $("#username").sendKeys("admin");
        $("#password").sendKeys("secret124");
        $(".primary").click();
        $(".alert-danger")
                .as("Приняты неверные учетные данные")
                .shouldHave(text("Неверные учетные данные пользователя"));
    }

    @Test
    void reloadcart() {
        String id = $("button[data-name='good1']").getAttribute("data-id");
        $("button[data-name='good1']").click();
        $("#open-cart-btn").click();
        com.codeborne.selenide.Selenide.refresh();
        $("#cart-item-" + id + " > div:nth-child(1) > b")
                .as("Товар исчез из корзины после обновления страницы")
                .shouldHave(text("good1"), Duration.ofSeconds(10));
    }

    @Test
    void ordermorethan300rub() {
        $("a[href='/admin']").click();
        $("#username").sendKeys("admin");
        $("#password").sendKeys("secret123");
        $(".primary").click();
        $("input#n-name").sendKeys("newgood");
        $("input#n-price").sendKeys("1337");
        $("#add-btn").click();
        $("div:nth-child(1) > a").click();
        String priceAttr = $("button[data-name='newgood']").getAttribute("data-price");
        int price = Integer.parseInt(priceAttr != null ? priceAttr : "0");
        if (price >= 300) {
            $("button[data-name='newgood']").click();
            $("#open-cart-btn").click();
            $("#makeOrder").click();
            confirm("[SmartShop]: Денег не хватает! Сумма " + price + " ₽ превышает лимит 300 ₽.");
        } else {
            $("button[data-name='newgood']").click();
            $("button[data-name='newgood']").click();
            int totalPrice = price * 2;
            $("#open-cart-btn").click();
            $("#makeOrder").click();
            confirm("[SmartShop]: Денег не хватает! Сумма " + totalPrice + " ₽ превышает лимит 300 ₽.");
        }
    }
}