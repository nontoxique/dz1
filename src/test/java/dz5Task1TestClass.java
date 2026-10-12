import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class dz5Task1TestClass {
    WebDriver driver;
    @BeforeEach
    void setup () {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }
    @AfterEach
    void shutdown () {
        driver.quit();
    }
    @Test
    void addgoodadmin () {
        driver.findElement(By.cssSelector("a[href='/admin']")).click();
driver.findElement(By.cssSelector("#username")).sendKeys("admin");
driver.findElement(By.cssSelector("#password")).sendKeys("secret123");
driver.findElement(By.cssSelector(".primary")).click();
driver.findElement(By.cssSelector("input#n-name")).sendKeys("newgood");
driver.findElement(By.cssSelector("input#n-price")).sendKeys("1337");
driver.findElement(By.cssSelector("#add-btn")).click();
driver.findElement(By.cssSelector("div:nth-child(1) > a")).click();
assertThat(driver.findElement(By.cssSelector("[data-name='newgood']:not([class='btn'])")))
        .as("Новый товар не появился");
    }
    @Test
    void addtocart () {
        String id = driver.findElement(By.cssSelector("button[data-name=\"newgood\"]")).getAttribute("data-id");
        driver.findElement(By.cssSelector("button[data-name=\"newgood\"]")).click();
        driver.findElement(By.cssSelector("#open-cart-btn")).click();

        assertThat(driver.findElement(By.cssSelector("#cart-item-"+id+"> div:nth-child(1) > b")).getText())
                .as("Товар не появился в корзине")
                .isEqualTo("newgood");



    }
    @Test
    void wrongcreds() {
        driver.findElement(By.cssSelector("a[href='/admin']")).click();
        driver.findElement(By.cssSelector("#username")).sendKeys("admin");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret124");
        driver.findElement(By.cssSelector(".primary")).click();
        assertThat(driver.findElement(By.cssSelector(".alert-danger")).getText())
                .as("Приняты неверные авторизационные данные")
                .isEqualTo("Неверные учетные данные пользователя");


    }
    @Test
    void reloadcart() {
        String id = driver.findElement(By.cssSelector("button[data-name=\"newgood\"]")).getAttribute("data-id");
        driver.findElement(By.cssSelector("button[data-name=\"newgood\"]")).click();
        driver.findElement(By.cssSelector("#open-cart-btn")).click();

        driver.navigate().refresh();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By cartItemLocator = By.cssSelector("#cart-item-" + id + " > div:nth-child(1) > b");

        String actualText = "";
        try {
            actualText = wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemLocator)).getText();
        } catch (org.openqa.selenium.TimeoutException e) {
            org.assertj.core.api.Assertions.fail("Товар исчез из корзины после обновления страницы");
        }
        assertThat(actualText)
                .as("Название товара в корзине не совпадает")
                .isEqualTo("newgood");
    }
}
