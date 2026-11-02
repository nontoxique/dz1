package dz8;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Config.ConfigProvider.CONFIG;
import static com.codeborne.selenide.Selenide.$x;

public class dz8TestClassTask1authpage {

    public SelenideElement loginForm = $x("//input[@id='username']");
    public SelenideElement passwordForm = $x("//input[@name='password']");
    public SelenideElement signInButton = $x("//button[text()='Sign in']");

    @BeforeEach
    void openChrome() {
        Selenide.open("http://localhost:8080/login");
    }

    public void fillInLoginForm(String login) {
        loginForm.setValue(login);
    }

    public void fillInPasswordFromConfig() {
        passwordForm.setValue(CONFIG.adminPassword());
    }

    public void clickSignIn() {
        signInButton.click();
    }

    public void loginAsAdmin() {
        fillInLoginForm(CONFIG.adminLogin());
        fillInPasswordFromConfig();
        clickSignIn();
    }

    @Test
    public void loginFormTest() {
        loginForm.shouldBe(Condition.visible);
    }

    @Test
    public void passwordFormTest() {
        passwordForm.shouldBe(Condition.visible);
    }

    @Test
    public void signInTest() {
        signInButton.shouldHave(Condition.text("Sign in"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void successfulAuthTest() {
        loginAsAdmin();
        $x("/html/body/div[2]/div[1]/h1").shouldHave(Condition.text("SmartShop Admin"));
    }
}
