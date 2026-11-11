package dz8.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPageAssert extends MainPage {

    public MainPageAssert clickAddButton(String goodName) {
        $("button[data-name='" + goodName + "']").click();
        return this;
    }

    public double getGoodPrice(String goodName) {
        String priceAttr = $("button[data-name='" + goodName + "']").getAttribute("data-price");
        return Double.parseDouble(priceAttr != null ? priceAttr : "0");
    }

    public MainPageAssert openCart() {
        openCartBtn.click();
        return this;
    }

    public MainPageAssert makeOrder() {
        makeOrderBtn.click();
        return this;
    }

    public void checkOrderProcessed() {
        $(byText("Заказ принят в обработку!")).shouldBe(visible);
    }

    public void checkTotalPrice(int expectedPrice) {
        totalPrice.shouldBe(visible).shouldHave(text(String.valueOf(expectedPrice)));
    }

    public void checkGoodCardText(int goodId, String expectedText) {
        $("#card-" + goodId).shouldHave(text(expectedText));
    }

    public LoginPageAssert clickAdminLink() {
        adminLink.click();
        return new LoginPageAssert();
    }
}
