package hr.unizg.fer.backend.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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

public class AdminUserTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait; // Short wait for edge cases

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/petarmajic/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Short wait for edge cases
        driver.manage().window().maximize();
    }

    @Test
    public void testAdminCanUpdateUserRole() throws InterruptedException {
        // 1. Otvori početnu stranicu
        driver.get("https://restore-films-frontend.onrender.com");

        // 2. Čekanje na učitavanje stranice i klik na "Users"
        wait.until(ExpectedConditions.urlContains("/home"));
        WebElement usersButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Users')]")));
        usersButton.click();

        // 3. Čekanje da se učita lista korisnika
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("user-list")));

        // 4. Pronalaženje korisnika "Petar Jakuš" unutar klase "user-item"
        WebElement userItem;
        try {
            userItem = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(@class,'user-item')]//strong[text()='Petar' and text()='Jakuš']")));
        } catch (Exception e) {
            throw new AssertionError("Test Failed: User 'Petar Jakuš' not found within the short wait time.", e);
        }

        // 5. Klik na select za tog korisnika
        WebElement selectElement = userItem.findElement(By.xpath(".//following-sibling::select"));
        selectElement.click(); // Otvori padajući izbornik

        // 6. Korištenje JavaScript za odabir opcije "VODITELJ"
        WebElement optionVoditelj = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(".//option[@value='VODITELJ']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].selected = true;", optionVoditelj);
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change'));", selectElement);

        // 7. Provjera da li je opcija "VODITELJ" odabrana
        Select select = new Select(selectElement);
        String selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, "VODITELJ", "Test Failed: Option 'VODITELJ' not selected for Petar Jakuš.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
