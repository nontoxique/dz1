package dz8.pages;
import com.codeborne.selenide.SelenideElement;
import dz8.pages.loginPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class mainPage {
    private final SelenideElement openCartBtn = $("#open-cart-btn");
    private final SelenideElement makeOrderBtn = $("#makeOrder");
    private final SelenideElement totalPrice = $("#total-price");
    private final SelenideElement adminLink = $("a[href='/admin']");

    public mainPage clickAddButton(String goodName) {
        $("button[data-name='" + goodName + "']").click();
        return this;
    }

    public double getGoodPrice(String goodName) {
        String priceAttr = $("button[data-name='" + goodName + "']").getAttribute("data-price");
        return Double.parseDouble(priceAttr != null ? priceAttr : "0");
    }

    public mainPage openCart() {
        openCartBtn.click();
        return this;
    }

    public mainPage makeOrder() {
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

    public loginPage clickAdminLink() {
        adminLink.click();
        return new loginPage();
    }
}
