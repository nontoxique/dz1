import Config.ConfigProvider;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Map;

import static Config.ConfigProvider.CONFIG;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
@Tag ("UItestdz")
public class dz7TestClass {

    private int good1Id;
    private final SelenideElement cartbutton = $x("//*[@id='open-cart-btn']");

    @BeforeAll
    public static void setUp() {
        ConfigProvider.logConfig();
    }

    @Step("Создание товара {name} с ценой {price} через API")
    public int addgoodapi(String name, double price) {
        Map<String, Object> body = Map.of(
                "name", name,
                "price", price
        );

        Response response = given()
                .baseUri(CONFIG.baseUrl())
                .contentType(ContentType.JSON)
                .body(body)
                .auth()
                .basic(CONFIG.adminLogin(), CONFIG.adminPassword())
                .post("/goods/add");

        Assertions.assertThat(response.statusCode())
                .as("Код ответа при создании товара должен быть 200")
                .isEqualTo(200);

        return response.path("data.id");
    }

    @BeforeEach
    public void prepareData() {
        Allure.step("Подготовка тестового товара и открытие главной страницы магазина", () -> {
            good1Id = addgoodapi(CONFIG.firstItemName(), CONFIG.firstItemPrice());
            Selenide.open(CONFIG.baseUrl());
        });
    }

    @AfterEach
    void deleteAndClose() {
        Allure.step("Удаление тестового товара (ID: " + good1Id + ") и закрытие браузера", () -> {
            given()
                    .baseUri(CONFIG.apiUrl())
                    .auth().basic(CONFIG.adminLogin(), CONFIG.adminPassword())
                    .delete("/goods/" + good1Id);

            Selenide.closeWebDriver();
        });
    }

    @Test
    void dnd() {
        Allure.step("Перетаскивание карточки товара (ID: " + good1Id + ") в кнопку корзины", () -> {
            sleep(1000);
            SelenideElement firstcard = $x("//*[@id='card-" + good1Id + "']");
            firstcard.dragAndDrop(DragAndDropOptions.to(cartbutton));
            sleep(2000);
        });

        Allure.step("Открытие корзины и проверка наличия товара '" + CONFIG.firstItemName() + "'", () -> {
            $("#open-cart-btn").click();
            $("#cart-item-" + good1Id + " > div:nth-child(1) > b")
                    .as("Товар не появился в корзине")
                    .shouldHave(text(CONFIG.firstItemName()));
        });
    }

    @Test
    void emptycart() {
        SelenideElement targetButton = $("button[data-name='" + CONFIG.firstItemName() + "']");

        String id = Allure.step("Получение ID товара '" + CONFIG.firstItemName() + "'", () ->
                targetButton.getAttribute("data-id")
        );

        Allure.step("Добавление товара в корзину и открытие корзины", () -> {
            targetButton.click();
            $("#open-cart-btn").click();
        });

        Allure.step("Проверка наличия добавленного товара в корзине", () -> {
            $("#cart-item-" + id + " > div:nth-child(1) > b")
                    .as("Товар не появился в корзине")
                    .shouldHave(text(CONFIG.firstItemName()));
        });

        Allure.step("Удаление товара из корзины и проверка сообщения о пустой корзине", () -> {
            $("button[data-action='remove'][data-index='0']").click();
            $("#empty-cart")
                    .as("Товар не исчез из корзины")
                    .shouldHave(text("Пусто"));
        });
    }
}
