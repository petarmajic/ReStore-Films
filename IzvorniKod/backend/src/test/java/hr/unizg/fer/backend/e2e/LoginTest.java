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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/petarmajic/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @Test
    @Parameters({"username", "password"})
    public void testMicrosoftLogin(String username, String password) {
        driver.get("https://restore-films-frontend.onrender.com/");

        clickElement(By.xpath("//button[contains(text(),'Sign in')]"), wait);

        enterText(By.name("loginfmt"), username, wait);
        clickElement(By.id("idSIButton9"), wait);

        if (isElementPresent(By.xpath("//div[contains(text(),'To korisničko ime možda nije ispravno.')]"))) {
            Assert.fail("Test Failed: To korisničko ime možda nije ispravno.");
        }

        enterText(By.name("passwd"), password, wait);
        clickElement(By.id("idSIButton9"), wait);

        if (isElementPresent(By.xpath("//div[contains(text(),'Račun ili lozinka nisu točni.')]"))) {
            Assert.fail("Test Failed: Račun ili lozinka nisu točni.");
        }

        if (!wait.until(ExpectedConditions.urlContains("/home"))) {
            throw new AssertionError("Test Failed: Took too long to load home page.");
        }

        System.out.println("Test Passed: Login successful.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void enterText(By locator, String text, WebDriverWait wait) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(text);
    }

    private void clickElement(By locator, WebDriverWait wait) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    private boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
