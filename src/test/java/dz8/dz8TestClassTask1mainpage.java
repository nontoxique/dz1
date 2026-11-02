package dz8;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;

public class dz8TestClassTask1mainpage {
    @BeforeEach
    void openchrome ()
    {
        Selenide.open("http://localhost:8080");
    }
    public SelenideElement adminpanel = $x("//a[@href='/admin']");
    public SelenideElement cartbutton = $x("//button[@id='open-cart-btn']");
    public SelenideElement item36 = $x("//button[@data-action='add-to-cart' and @data-id='36']");
    public SelenideElement onemoreitem36 = $x("//button[@data-id='36' and @data-action='qty-change' and text()='+']");
    public SelenideElement onelessitem36 = $x("//button[@data-id='36' and @data-action='qty-change' and text()='-']");
    public SelenideElement changenumber36 = $x("//input[@id='q-36' and @type='number' and @min='1']");

    public void clickadminpanel() {
        adminpanel.click();
    }

    public void opencart() {
        cartbutton.click();
    }

    public void additem36() {
        item36.click();
    }

    public void plusitem36() {
        onemoreitem36.click();
    }

    public void minusitem36() {
        onelessitem36.click();
    }

    public void insertnumberfor36(Integer number) {
        changenumber36.setValue(String.valueOf(number));
    }

    @Test
    public void adminpaneltest() {
        adminpanel.shouldHave(Condition.text("Администрирование"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void cartbuttontest() {
        cartbutton.shouldHave(Condition.text("Корзина"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void item36test() {
        item36.shouldHave(Condition.text("В корзину"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void onemoreitem36test() {
        onemoreitem36.shouldBe(Condition.visible);
    }

    @Test
    public void onelessitem36test() {
        onelessitem36.shouldBe(Condition.visible);
    }

    @Test
    public void changenumber36test() {
        changenumber36.shouldBe(Condition.visible);
    }
}
