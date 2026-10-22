import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static Config.config.CONFIG;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class dz7TestClass {

    private int good1Id;


    private final SelenideElement cartbutton = $x("//*[@id='open-cart-btn']");

    @BeforeAll
    public static void setUp() {
        ConfigManager.printConfigToConsole();
    }

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
                .as("Код ответа при создании товара должен быть 200")
                .isEqualTo(200);

        return response.path("data.id");
    }

    @BeforeEach
    public void prepareData() {
        good1Id = addgoodapi(CONFIG.firstItemName(), CONFIG.firstItemPrice());
        Selenide.open(CONFIG.baseUrl());
    }

    @AfterEach
    void deleteAndClose() {
        given()
                .baseUri(CONFIG.apiUrl())
                .auth().basic(CONFIG.adminLogin(), CONFIG.adminPassword())
                .delete("/goods/" + good1Id);

        Selenide.closeWebDriver();
    }

    @Test
    void dnd() {
        sleep(1000);
        SelenideElement firstcard = $x("//*[@id='card-" + good1Id + "']");
        firstcard.dragAndDrop(DragAndDropOptions.to(cartbutton));
        sleep(2000);
    }

    @Test
    void emptycart() {
        SelenideElement targetButton = $("button[data-name='" + CONFIG.firstItemName() + "']");

        String id = targetButton.getAttribute("data-id");

        targetButton.click();
        $("#open-cart-btn").click();

        $("#cart-item-" + id + " > div:nth-child(1) > b")
                .as("Товар не появился в корзине")
                .shouldHave(text(CONFIG.firstItemName()));

        $("button[data-action='remove'][data-index='0']").click();

        $("#empty-cart")
                .as("Товар не исчез из корзины")
                .shouldHave(text("Пусто"));
    }
}
