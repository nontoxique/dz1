package dz8.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    protected final SelenideElement usernameField = $("#username");
    protected final SelenideElement passwordField = $("#password");
    protected final SelenideElement loginBtn = $(".primary");
}
