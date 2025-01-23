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
            // Otvaranje početnog URL-a
            driver.get("https://restore-films-frontend.onrender.com");

            // Čekanje da se URL promijeni na /home
            wait.until(ExpectedConditions.urlContains("/home"));

            // Klik na gumb "Scan" na stranici /home
            WebElement scanButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Scan Barcode']")));
            scanButton.click();

            // Nastavak s testiranjem dodavanja filma
            WebElement manualInputButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Manual input']")));
            manualInputButton.click();

            // Unos podataka za film
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//label[contains(text(), 'Original title:')]/input")))
                    .sendKeys("Novi test film 2");

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

            // Klik na gumb "Add"
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Add']")));
            addButton.click();

            // Provjera za poruku "Fail to add"
            WebElement failMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//p[contains(text(), 'Failed to add film. Please try again.')]")));
            throw new AssertionError("Test failed: Duplicate film detected on screen");
        } catch (Exception e) {
            // Ako poruka "Fail to add" nije pronađena, nastavljamo provjeru dodanog filma
            WebElement barcodeList = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.className("scan-br-list")));

            Assert.assertTrue(barcodeList.getText().contains("Novi test film 2"),
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