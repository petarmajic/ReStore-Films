package hr.unizg.fer.backend.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class DigitalizationTest {
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
    public void testOnDigitalizationList() throws InterruptedException {
        driver.get("https://restore-films-frontend.onrender.com");

        wait.until(ExpectedConditions.urlContains("/home"));

        WebElement digitalizationButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Digitization')]")));
        digitalizationButton.click();

        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//select")));
        selectElement.click();

        Select select = new Select(selectElement);
        select.selectByVisibleText("On Digitalization");

        String selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, "On Digitalization", "Test Failed: Option 'On Digitalization' not selected.");

        Thread.sleep(5000);
    }

    @Test
    public void testFinishedList() throws InterruptedException {
        driver.get("https://restore-films-frontend.onrender.com");

        wait.until(ExpectedConditions.urlContains("/home"));

        WebElement digitalizationButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Digitization')]")));
        digitalizationButton.click();

        WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//select")));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Finished");

        // Provjera je li "Finished"  odabrano
        String selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, "Finished", "Test Failed: Option 'Finished' not selected.");

        Thread.sleep(5000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
