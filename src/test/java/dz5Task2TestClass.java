import Config.ConfigProvider;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
@Tag("UItestdz")
public class dz5Task2TestClass {

    private int good1Id;

    @BeforeAll
    static void setUpConfiguration() {
        ConfigProvider.logConfig();
    }
    @Step("Создание товара {name} с ценой {price} через API")
    public int addgoodapi(String name, double price) {
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
        Allure.step("Подготовка тестовых данных и открытие главной страницы", () -> {
            good1Id = addgoodapi("good1", 70);
            Selenide.open("http://localhost:8080");
        });
    }

    @AfterEach
    void deleteAndClose() {
        // Использование Allure.step позволяет динамически подставить актуальный good1Id без проблем с рефлексией
        Allure.step("Удаление тестового товара (ID: " + good1Id + ") и закрытие браузера", () -> {
            given()
                    .baseUri("http://localhost:8080")
                    .auth().basic("admin", "secret123")
                    .delete("/goods/" + good1Id);
            Selenide.closeWebDriver();
        });
    }

    @Test
    void addgoodadmin() {
        Allure.step("Переход в панель администратора", () -> {
            $("a[href='/admin']").click();
        });
        Allure.step("Авторизация под учетной записью администратора", () -> {
            $("#username").sendKeys("admin");
            $("#password").sendKeys("secret123");
            $(".primary").click();
        });
        Allure.step("Добавление нового товара 'newgood' с ценой 1337", () -> {
            $("input#n-name").sendKeys("newgood");
            $("input#n-price").sendKeys("1337");
            $("#add-btn").click();
        });
        Allure.step("Возврат на главную страницу магазина", () -> {
            $("div:nth-child(1) > a").click();
        });
        Allure.step("Проверка отображения нового товара на главной странице", () -> {
            $("[data-name='newgood']:not([class='btn'])")
                    .as("Новый товар не появился")
                    .shouldBe(visible);
        });
    }

    @Test
    void addtocart() {
        String id = Allure.step("Получение ID товара 'newgood'", () ->
                $("button[data-name='newgood']").getAttribute("data-id")
        );
        Allure.step("Добавление товара 'newgood' в корзину", () -> {
            $("button[data-name=\"newgood\"]").click();
        });
        Allure.step("Открытие корзины", () -> {
            $("#open-cart-btn").click();
        });
        Allure.step("Проверка наличия товара 'newgood' в корзине", () -> {
            $("#cart-item-" + id + "> div:nth-child(1) > b")
                    .as("Товар не появился в корзине")
                    .shouldHave(text("newgood"));
        });
    }

    @Test
    void wrongcreds() {
        Allure.step("Переход в панель администратора", () -> {
            $("a[href='/admin']").click();
        });
        Allure.step("Ввод неверных учетных данных администратора", () -> {
            $("#username").sendKeys("admin");
            $("#password").sendKeys("secret124");
            $(".primary").click();
        });
        Allure.step("Проверка сообщения об ошибке авторизации", () -> {
            $(".alert-danger")
                    .as("Приняты неверные учетные данные")
                    .shouldHave(text("Неверные учетные данные пользователя"));
        });
    }

    @Test
    void reloadcart() {
        String id = Allure.step("Получение ID товара 'good1'", () ->
                $("button[data-name='good1']").getAttribute("data-id")
        );
        Allure.step("Добавление товара 'good1' в корзину и переход в неё", () -> {
            $("button[data-name='good1']").click();
            $("#open-cart-btn").click();
        });
        Allure.step("Обновление страницы", () -> {
            Selenide.refresh();
        });
        Allure.step("Проверка сохранения товара 'good1' в корзине после обновления", () -> {
            $("#cart-item-" + id + " > div:nth-child(1) > b")
                    .as("Товар исчез из корзины после обновления страницы")
                    .shouldHave(text("good1"), Duration.ofSeconds(10));
        });
    }

    @Test
    void ordermorethan300rub() {
        Allure.step("Переход в панель администратора и авторизация", () -> {
            $("a[href='/admin']").click();
            $("#username").sendKeys("admin");
            $("#password").sendKeys("secret123");
            $(".primary").click();
        });
        Allure.step("Создание товара 'newgood' с ценой 1337 и возврат на главную", () -> {
            $("input#n-name").sendKeys("newgood");
            $("input#n-price").sendKeys("1337");
            $("#add-btn").click();
            $("div:nth-child(1) > a").click();
        });
        int price = Allure.step("Получение цены товара 'newgood'", () -> {
            String priceAttr = $("button[data-name='newgood']").getAttribute("data-price");
            return Integer.parseInt(priceAttr != null ? priceAttr : "0");
        });
        Allure.step("Добавление товара в корзину на сумму свыше 300 ₽ и попытка оформления заказа", () -> {
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
        });
    }
}
