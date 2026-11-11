package dz8.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class AdminPage {
    protected final SelenideElement newNameField = $("input#n-name");
    protected final SelenideElement newPriceField = $("input#n-price");
    protected final SelenideElement addBtn = $("#add-btn");
    protected final SelenideElement toastNotification = $("#toast-container > div");
    protected final SelenideElement backToMainLink = $("body > div.container > div:nth-child(1) > a");
}
