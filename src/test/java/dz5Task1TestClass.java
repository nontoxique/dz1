import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
@Tag("UItestdz")
public class dz5Task1TestClass {
    WebDriver driver;

    @BeforeEach
    void setup() {
        Allure.step("Открытие браузера и главной страницы магазина", () -> {
            driver = new ChromeDriver();
            driver.get("http://localhost:8080");
        });
    }

    @AfterEach
    void shutdown() {
        Allure.step("Закрытие браузера", () -> {
            if (driver != null) {
                driver.quit();
            }
        });
    }

    @Test
    void addgoodadmin() {
        Allure.step("Переход в панель администратора", () -> {
            driver.findElement(By.cssSelector("a[href='/admin']")).click();
        });

        Allure.step("Авторизация под учетной записью администратора", () -> {
            driver.findElement(By.cssSelector("#username")).sendKeys("admin");
            driver.findElement(By.cssSelector("#password")).sendKeys("secret123");
            driver.findElement(By.cssSelector(".primary")).click();
        });

        Allure.step("Создание нового товара 'newgood' с ценой 1337", () -> {
            driver.findElement(By.cssSelector("input#n-name")).sendKeys("newgood");
            driver.findElement(By.cssSelector("input#n-price")).sendKeys("1337");
            driver.findElement(By.cssSelector("#add-btn")).click();
        });

        Allure.step("Возврат на главную страницу магазина", () -> {
            driver.findElement(By.cssSelector("div:nth-child(1) > a")).click();
        });

        Allure.step("Проверка отображения нового товара на главной странице", () -> {
            assertThat(driver.findElement(By.cssSelector("[data-name='newgood']:not([class='btn'])")))
                    .as("Новый товар не появился")
                    .isNotNull();
        });
    }

    @Test
    void addtocart() {
        String id = Allure.step("Получение ID товара 'newgood'", () ->
                driver.findElement(By.cssSelector("button[data-name=\"newgood\"]")).getAttribute("data-id")
        );

        Allure.step("Добавление товара 'newgood' в корзину", () -> {
            driver.findElement(By.cssSelector("button[data-name=\"newgood\"]")).click();
        });

        Allure.step("Открытие корзины", () -> {
            driver.findElement(By.cssSelector("#open-cart-btn")).click();
        });

        Allure.step("Проверка наличия товара 'newgood' в корзине", () -> {
            assertThat(driver.findElement(By.cssSelector("#cart-item-" + id + "> div:nth-child(1) > b")).getText())
                    .as("Товар не появился в корзине")
                    .isEqualTo("newgood");
        });
    }

    @Test
    void wrongcreds() {
        Allure.step("Переход в панель администратора", () -> {
            driver.findElement(By.cssSelector("a[href='/admin']")).click();
        });

        Allure.step("Попытка авторизации с неверным паролем", () -> {
            driver.findElement(By.cssSelector("#username")).sendKeys("admin");
            driver.findElement(By.cssSelector("#password")).sendKeys("secret124");
            driver.findElement(By.cssSelector(".primary")).click();
        });

        Allure.step("Проверка сообщения об ошибке авторизации", () -> {
            assertThat(driver.findElement(By.cssSelector(".alert-danger")).getText())
                    .as("Приняты неверные авторизационные данные")
                    .isEqualTo("Неверные учетные данные пользователя");
        });
    }

    @Test
    void reloadcart() {
        String id = Allure.step("Получение ID товара 'newgood'", () ->
                driver.findElement(By.cssSelector("button[data-name=\"newgood\"]")).getAttribute("data-id")
        );

        Allure.step("Добавление товара 'newgood' в корзину и переход в неё", () -> {
            driver.findElement(By.cssSelector("button[data-name=\"newgood\"]")).click();
            driver.findElement(By.cssSelector("#open-cart-btn")).click();
        });

        Allure.step("Обновление страницы", () -> {
            driver.navigate().refresh();
        });

        Allure.step("Проверка сохранения товара в корзине после обновления страницы", () -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By cartItemLocator = By.cssSelector("#cart-item-" + id + " > div:nth-child(1) > b");
            String actualText = "";
            try {
                actualText = wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemLocator)).getText();
            } catch (TimeoutException e) {
                fail("Товар исчез из корзины после обновления страницы");
            }
            assertThat(actualText)
                    .as("Название товара в корзине не совпадает")
                    .isEqualTo("newgood");
        });
    }
}
