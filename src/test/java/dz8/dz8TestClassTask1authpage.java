package dz8;

import Config.ConfigProvider;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static Config.ConfigProvider.CONFIG;
import static com.codeborne.selenide.Selenide.$x;
@Tag("UItestdz")

public class dz8TestClassTask1authpage {
    @BeforeAll
    public static void setUp() {
        ConfigProvider.logConfig();
    }

    public SelenideElement loginForm = $x("//input[@id='username']");
    public SelenideElement passwordForm = $x("//input[@name='password']");
    public SelenideElement signInButton = $x("//button[text()='Sign in']");

    @BeforeEach
    @Step("Открытие страницы авторизации")
    void openChrome() {
        Selenide.open("http://localhost:8080/login");
    }

    @Step("Ввод логина '{login}'")
    public void fillInLoginForm(String login) {
        loginForm.setValue(login);
    }

    @Step("Ввод пароля администратора из конфигурации")
    public void fillInPasswordFromConfig() {
        passwordForm.setValue(CONFIG.adminPassword());
    }

    @Step("Нажатие кнопки 'Sign in'")
    public void clickSignIn() {
        signInButton.click();
    }

    @Step("Авторизация под учетной записью администратора")
    public void loginAsAdmin() {
        fillInLoginForm(CONFIG.adminLogin());
        fillInPasswordFromConfig();
        clickSignIn();
    }

    @Test
    public void loginFormTest() {
        Allure.step("Проверка видимости поля ввода логина", () -> {
            loginForm.shouldBe(Condition.visible);
        });
    }

    @Test
    public void passwordFormTest() {
        Allure.step("Проверка видимости поля ввода пароля", () -> {
            passwordForm.shouldBe(Condition.visible);
        });
    }

    @Test
    public void signInTest() {
        Allure.step("Проверка видимости и текста кнопки 'Sign in'", () -> {
            signInButton.shouldHave(Condition.text("Sign in"))
                    .shouldBe(Condition.visible);
        });
    }

    @Test
    public void successfulAuthTest() {
        loginAsAdmin();

        Allure.step("Проверка отображения заголовка 'SmartShop Admin' после входа", () -> {
            $x("/html/body/div[2]/div[1]/h1").shouldHave(Condition.text("SmartShop Admin"));
        });
    }
}
