package dz8;

import Config.ConfigProvider;
import com.codeborne.selenide.Selenide;
import dz8.pages.AdminPageAssert;
import dz8.pages.MainPageAssert;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static Config.ConfigProvider.CONFIG;
import static io.restassured.RestAssured.given;
@Tag("UItestdz")
public class dz8TestClassTask2 {
    @BeforeAll
    public static void setUp() {
        ConfigProvider.logConfig();
    }
    private int good1Id;
    private int good2Id;
    private int good3Id;
    private MainPageAssert mainPage;

    @Step("Создание товара '{name}' с ценой {price} через API")
    private int addgoodapi(String name, double price) {
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
                .as("Код ответа должен быть 200")
                .isEqualTo(200);

        return response.path("data.id");
    }

    @BeforeEach
    @Step("Подготовка трех тестовых товаров и открытие главной страницы магазина")
    public void prepareData() {
        good1Id = addgoodapi(CONFIG.firstItemName(), CONFIG.firstItemPrice());
        good2Id = addgoodapi("good2", 100);
        good3Id = addgoodapi("good3", 120);

        mainPage = Selenide.open(CONFIG.baseUrl(), MainPageAssert.class);
    }

    @AfterEach
    @Step("Удаление тестовых товаров (ID: {this.good1Id}, {this.good2Id}, {this.good3Id}) и закрытие браузера")
    void deleteandclose() {
        List.of(good1Id, good2Id, good3Id).forEach(id -> {
            given()
                    .baseUri(CONFIG.baseUrl())
                    .auth().basic(CONFIG.adminLogin(), CONFIG.adminPassword())
                    .delete("/goods/" + id);
        });
        Selenide.closeWebDriver();
    }

    @Test
    public void add3goods() {
        Allure.step("Добавление трех товаров в корзину", () -> {
            mainPage.clickAddButton(CONFIG.firstItemName())
                    .clickAddButton("good2")
                    .clickAddButton("good3");
        });

        Allure.step("Открытие корзины, оформление заказа и проверка его подтверждения", () -> {
            mainPage.openCart()
                    .makeOrder()
                    .checkOrderProcessed();
        });
    }

    @Test
    public void checksum() {
        int expectedTotal = Allure.step("Получение цен трех товаров и добавление их в корзину", () -> {
            double g1 = mainPage.getGoodPrice(CONFIG.firstItemName());
            mainPage.clickAddButton(CONFIG.firstItemName());

            double g2 = mainPage.getGoodPrice("good2");
            mainPage.clickAddButton("good2");

            double g3 = mainPage.getGoodPrice("good3");
            mainPage.clickAddButton("good3");

            return (int) (g1 + g2 + g3);
        });

        Allure.step("Открытие корзины и проверка итоговой суммы (" + expectedTotal + ")", () -> {
            mainPage.openCart()
                    .checkTotalPrice(expectedTotal);
        });
    }

    @Test
    public void addasadmin() {
        AdminPageAssert adminPage = Allure.step("Переход в панель администратора и авторизация", () ->
                mainPage.clickAdminLink()
                        .loginAs(CONFIG.adminLogin(), CONFIG.adminPassword())
        );

        Allure.step("Добавление нового товара 'anothernewgood' с ценой 1337 и проверка уведомления", () -> {
            adminPage.addNewGood("anothernewgood", "1337")
                    .checkToastText("Товар успешно добавлен!");
        });
    }

    @Test
    public void editgood() {
        AdminPageAssert adminPage = Allure.step("Переход в панель администратора и авторизация", () ->
                mainPage.clickAdminLink()
                        .loginAs(CONFIG.adminLogin(), CONFIG.adminPassword())
        );

        Allure.step("Переименование товара с ID " + good1Id + " на 'renamedgood' и проверка карточки на главной", () -> {
            adminPage.editGoodName(good1Id, "renamedgood")
                    .clickBackToMain()
                    .checkGoodCardText(good1Id, "renamedgood");
        });
    }
}
