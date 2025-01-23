package hr.unizg.fer.backend.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class BarcodeScannerTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/petarmajic/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        driver.manage().window().maximize();
    }

    @Test
    public void testAddFilm() {
        try {
            driver.get("https://restore-films-frontend.onrender.com");

            wait.until(ExpectedConditions.urlContains("/home"));

            WebElement scanButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Scan Barcode']")));
            scanButton.click();

            WebElement manualInputButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Manual input']")));
            manualInputButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//label[contains(text(), 'Original title:')]/input")))
                    .sendKeys("Unique name");

            driver.findElement(By.xpath("//label[contains(text(), 'Original language:')]/input"))
                    .sendKeys("Croatian");
            driver.findElement(By.xpath("//label[contains(text(), 'Country of origin:')]/input"))
                    .sendKeys("Croatia");

            WebElement yearInput = driver.findElement(By.xpath("//label[contains(text(), 'Manufacturing year:')]/input"));
            yearInput.clear();
            yearInput.sendKeys("2023");

            WebElement durationInput = driver.findElement(By.xpath("//label[contains(text(), 'Duration:')]/input"));
            durationInput.clear();
            durationInput.sendKeys("02:30:00");

            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Add']")));
            addButton.click();

            WebElement failMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//p[contains(text(), 'Failed to add film. Please try again.')]")));
            throw new AssertionError("Test failed: Duplicate film detected on screen");
        } catch (Exception e) {
            WebElement barcodeList = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.className("scan-br-list")));

            Assert.assertTrue(barcodeList.getText().contains("Unique name"),
                    "Film was not added successfully");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}