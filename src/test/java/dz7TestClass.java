import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;
import static io.restassured.RestAssured.given;

public class dz7TestClass {
        SelenideElement firstcard = $x("//*[@id='card-1']");
        SelenideElement cartbutton = $x("//*[@id='open-cart-btn']");
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
        good1Id = addgoodapi("good1", 3000);
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
        void dnd() {
            sleep(1000);
            firstcard.dragAndDrop(DragAndDropOptions.to(cartbutton));
            sleep(2000);
        }
    @Test
    void emptycart ()
    {
        String id = $("button[data-name='good1']").getAttribute("data-id");
        $("button[data-name='good1']").click();
        $("#open-cart-btn").click();

        $("#cart-item-" + id + " > div:nth-child(1) > b")
                .as("Товар не появился в корзине")
                .shouldHave(text("good1"));

        $("button[data-action='remove'][data-index='0']").click();

        $("#empty-cart")
                .as("Товар не исчез из корзины")
                .shouldHave(text("Пусто"));
    }

}
