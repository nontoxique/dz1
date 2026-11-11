package dz8;

import Config.ConfigProvider;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

@Tag("UItestdz")
public class dz8TestClassTask1mainpage {

    @BeforeAll
    public static void setUp() {
        ConfigProvider.logConfig();
    }

    public SelenideElement adminpanel = $x("//a[@href='/admin']");
    public SelenideElement cartbutton = $x("//button[@id='open-cart-btn']");
    public SelenideElement item36 = $x("//button[@data-action='add-to-cart' and @data-id='36']");
    public SelenideElement onemoreitem36 = $x("//button[@data-id='36' and @data-action='qty-change' and text()='+']");
    public SelenideElement onelessitem36 = $x("//button[@data-id='36' and @data-action='qty-change' and text()='-']");
    public SelenideElement changenumber36 = $x("//input[@id='q-36' and @type='number' and @min='1']");

    @BeforeEach
    void openchrome() {
        step("Открытие браузера и переход на главную страницу магазина", () -> {
            Selenide.open("http://localhost:8080");
        });
    }

    @AfterEach
    void closeBrowser() {
        step("Закрытие браузера", () -> {
            Selenide.closeWebDriver();
        });
    }

    @Step("Переход в панель администратора")
    public void clickadminpanel() {
        adminpanel.click();
    }

    @Step("Открытие корзины")
    public void opencart() {
        cartbutton.click();
    }

    @Step("Добавление товара с ID 36 в корзину")
    public void additem36() {
        item36.click();
    }

    @Step("Увеличение количества товара с ID 36 на 1")
    public void plusitem36() {
        onemoreitem36.click();
    }

    @Step("Уменьшение количества товара с ID 36 на 1")
    public void minusitem36() {
        onelessitem36.click();
    }

    @Step("Ввод количества {number} для товара с ID 36")
    public void insertnumberfor36(Integer number) {
        changenumber36.setValue(String.valueOf(number));
    }

    @Test
    public void adminpaneltest() {
        step("Проверка видимости и текста кнопки 'Администрирование'", () -> {
            adminpanel.shouldHave(Condition.text("Администрирование"))
                    .shouldBe(Condition.visible);
        });
    }

    @Test
    public void cartbuttontest() {
        step("Проверка видимости и текста кнопки 'Корзина'", () -> {
            cartbutton.shouldHave(Condition.text("Корзина"))
                    .shouldBe(Condition.visible);
        });
    }

    @Test
    public void item36test() {
        step("Проверка видимости и текста кнопки 'В корзину' у товара с ID 36", () -> {
            item36.shouldHave(Condition.text("В корзину"))
                    .shouldBe(Condition.visible);
        });
    }

    @Test
    public void onemoreitem36test() {
        step("Проверка видимости кнопки '+' для товара с ID 36", () -> {
            onemoreitem36.shouldBe(Condition.visible);
        });
    }

    @Test
    public void onelessitem36test() {
        step("Проверка видимости кнопки '-' для товара с ID 36", () -> {
            onelessitem36.shouldBe(Condition.visible);
        });
    }

    @Test
    public void changenumber36test() {
        step("Проверка видимости поля ввода количества для товара с ID 36", () -> {
            changenumber36.shouldBe(Condition.visible);
        });
    }
}
