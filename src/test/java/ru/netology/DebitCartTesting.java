package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.*;
import static org.openqa.selenium.By.cssSelector;

public class DebitCartTesting {
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");

    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSuccessTest() {
        driver.findElement(cssSelector("[data-test-id=name] .input__control ")).sendKeys("Елена Долгополова");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control ")).sendKeys("+79227576995");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

    @Test
    void shouldTestWithHyphen(){
        driver.findElement(cssSelector("[data-test-id=name] .input__control ")).sendKeys("Елена-Долгополова");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control ")).sendKeys("+79227576995");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.trim());

}
    @Test
    void testWithEngName() {
        driver.findElement(cssSelector("[data-test-id=name] .input__control ")).sendKeys("Yelena Dolgopolova");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control ")).sendKeys("+79227576995");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

    @Test
    void testWithoutName() {
        driver.findElement(cssSelector("[data-test-id=name] .input__control ")).sendKeys(" ");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control ")).sendKeys("+79227576995");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.trim());
    }
    @Test
    void testWithoutPhone() {
        driver.findElement(cssSelector("[data-test-id=name] .input__control ")).sendKeys("Елена Долгополова");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control ")).sendKeys("");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

    @Test
    void testWithUncorrectPhone() {
        driver.findElement(cssSelector("[data-test-id=name] .input__control ")).sendKeys("Елена Долгополова");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control ")).sendKeys("89227576995");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

    @Test
    void testWithUncorrectPhone2() {
        driver.findElement(cssSelector("[data-test-id=name] .input__control ")).sendKeys("Елена Долгополова");
        driver.findElement(cssSelector("[data-test-id=phone] .input__control ")).sendKeys("8922757699555555");
        driver.findElement(className("checkbox__box")).click();
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

    @Test
    void testCheckbox(){
        driver.findElement(cssSelector("[data-test-id='name'] .input__control ")).sendKeys("Елена Долгополова");
        driver.findElement(cssSelector("[data-test-id='phone'] .input__control ")).sendKeys("+79227576995");
        driver.findElement(tagName("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=agreement].input_invalid")).getText();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedText, actualText.trim());
    }
}
