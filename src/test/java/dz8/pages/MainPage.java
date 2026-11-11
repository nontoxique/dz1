package dz8.pages;
import com.codeborne.selenide.SelenideElement;
import dz8.pages.LoginPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    protected final SelenideElement openCartBtn = $("#open-cart-btn");
    protected final SelenideElement makeOrderBtn = $("#makeOrder");
    protected final SelenideElement totalPrice = $("#total-price");
    protected final SelenideElement adminLink = $("a[href='/admin']");
}
