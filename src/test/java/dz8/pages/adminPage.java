package dz8.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class adminPage {
    private final SelenideElement newNameField = $("input#n-name");
    private final SelenideElement newPriceField = $("input#n-price");
    private final SelenideElement addBtn = $("#add-btn");
    private final SelenideElement toastNotification = $("#toast-container > div");
    private final SelenideElement backToMainLink = $("body > div.container > div:nth-child(1) > a");

    public adminPage addNewGood(String name, String price) {
        newNameField.sendKeys(name);
        newPriceField.sendKeys(price);
        addBtn.click();
        return this;
    }

    public adminPage editGoodName(int goodId, String newName) {
        $("#nm-" + goodId).setValue(newName);
        $("button.btn-upd[data-id='" + goodId + "']").click();
        return this;
    }

    public dz8.pages.mainPage clickBackToMain() {
        backToMainLink.click();
        return new dz8.pages.mainPage();
    }

    public void checkToastText(String expectedText) {
        toastNotification.shouldHave(text(expectedText));
    }
}
