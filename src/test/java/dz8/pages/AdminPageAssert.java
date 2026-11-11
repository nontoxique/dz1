package dz8.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class AdminPageAssert extends AdminPage {

    public AdminPageAssert addNewGood(String name, String price) {
        newNameField.sendKeys(name);
        newPriceField.sendKeys(price);
        addBtn.click();
        return this;
    }

    public AdminPageAssert editGoodName(int goodId, String newName) {
        $("#nm-" + goodId).setValue(newName);
        $("button.btn-upd[data-id='" + goodId + "']").click();
        return this;
    }

    public MainPageAssert clickBackToMain() {
        backToMainLink.click();
        return new MainPageAssert();
    }

    public void checkToastText(String expectedText) {
        toastNotification.shouldHave(text(expectedText));
    }
}
