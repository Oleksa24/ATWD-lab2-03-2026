package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class lab2 {
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void preconditions() {
        String baseUrl = "https://www.ukrposhta.ua/ua";
        driver.get(baseUrl);
    }

    @Test
    public void testHeaderExists() {
        WebElement header = driver.findElement(By.id("main"));
        Assert.assertNotNull(header);
    }

    @Test
    public void testWriteText(){
        WebElement searchField = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div[1]/div/form/div/input"));
        Assert.assertNotNull(searchField);
    }

    @Test
    public void testButtonClick(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("page-preloader")));
        driver.findElement(By.id("calculate_button")).click();
    }

    @Test
    public void testByTagNameAndInfo() {
        WebElement element = driver.findElement(By.tagName("a"));
        System.out.println("--- Параметри елемента ---");
        System.out.println("Тег: " + element.getTagName());
        System.out.println("Текст: " + element.getText());
        System.out.println("Розташування (Point): " + element.getLocation());
        System.out.println("Розмір (Dimension): " + element.getSize());
        System.out.println("Відображається: " + element.isDisplayed());
        System.out.println("Доступний (Enabled): " + element.isEnabled());
        System.out.println("Вибраний (Selected): " + element.isSelected());
        System.out.println("Клас елемента: " + element.getAttribute("class"));
        System.out.println("Колір тексту (CSS): " + element.getCssValue("color"));
    }

    @Test
    public void testSearchWithEnter() {
        WebElement searchField = driver.findElement(By.id("trackcode"));
        String testData = "1234567890123";
        searchField.sendKeys(testData + Keys.ENTER);
        String actualValue = searchField.getAttribute("value");
        Assert.assertEquals(actualValue, testData, "Текст не збігається");
        Assert.assertFalse(actualValue.isEmpty(), "Поле порожнє");
    }

    @Test
    public void testNotStraightXpath() {
        WebElement input = driver.findElement(By.xpath("//input[starts-with(@id, 'track')]"));
        Assert.assertNotNull(input);
    }

    @Test
    public void testElementVisibility() {
        WebElement logo = driver.findElement(By.xpath("//img[@class='banner-img-desktop ls-is-cached lazyloaded']"));
        boolean isVisible = logo.isDisplayed();
        Assert.assertTrue(isVisible, "Логотип не відображається на сторінці!");
        System.out.println("Перевірка успішна: елемент видимий.");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}