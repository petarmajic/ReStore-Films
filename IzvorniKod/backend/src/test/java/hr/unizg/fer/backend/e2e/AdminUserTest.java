package hr.unizg.fer.backend.e2e;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class AdminUserTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/petarmajic/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Short wait for edge cases
        driver.manage().window().maximize();
    }

    @Test
    public void testAdminCanUpdateUserRole() throws TimeoutException, InterruptedException {
        driver.get("https://restore-films-frontend.onrender.com");

        wait.until(ExpectedConditions.urlContains("/home"));
        WebElement usersButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Users')]")));
        usersButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("user-list")));

        WebElement userItem = null;
        try {
            userItem = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(@class,'user-item')]//strong[text()='Petar' and text()='Jakuš']")));
        } catch (TimeoutException e) {
            Assert.fail("Test Failed: User 'Petar Jakuš' not found.");
        } catch (Exception e) {
            Assert.fail("Test Failed: An unexpected error occurred.");
        }

        WebElement selectElement = userItem.findElement(By.xpath(".//following-sibling::select"));
        selectElement.click();

        WebElement optionVoditelj = userItem.findElement(By.xpath(".//following-sibling::select//option[@value='VODITELJ']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].selected = true;", optionVoditelj);
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change'));", selectElement);

        Select select = new Select(selectElement);
        String selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, "VODITELJ", "Test Failed: Option 'VODITELJ' not selected for Petar Jakuš.");

        Thread.sleep(5000);
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
