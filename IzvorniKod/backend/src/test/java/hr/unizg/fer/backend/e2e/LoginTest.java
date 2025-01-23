package hr.unizg.fer.backend.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        // Postavljanje staze do ChromeDriver-a i pokretanje preglednika
        System.setProperty("webdriver.chrome.driver", "/Users/petarmajic/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Inicijalizacija čekanja
        driver.manage().window().maximize(); // Maksimizacija prozora preglednika
    }

    @Test
    @Parameters({"username", "password"}) // Parametri za prijavu (korisničko ime i lozinka)
    public void testMicrosoftLogin(String username, String password) {
        try {
            // Otvaranje početne stranice
            driver.get("https://restore-films-frontend.onrender.com/");

            // Klik na gumb "Sign in"
            clickElement(By.xpath("//button[contains(text(),'Sign in')]"), wait);

            // Unos korisničkog imena
            enterText(By.name("loginfmt"), username, wait);
            // Klik na gumb "Dalje" za potvrdu unosa korisničkog imena
            clickElement(By.id("idSIButton9"), wait);

            // Provjera za grešku vezanu uz neispravno korisničko ime
            checkForError(By.xpath("//div[contains(text(),'To korisničko ime možda nije ispravno.')]"),
                    "Test Failed: To korisničko ime možda nije ispravno.", wait);

            // Unos lozinke
            enterText(By.name("passwd"), password, wait);
            // Klik na gumb "Dalje" za potvrdu lozinke
            clickElement(By.id("idSIButton9"), wait);

            // Provjera za grešku vezanu uz neispravnu lozinku ili korisnički račun
            checkForError(By.xpath("//div[contains(text(),'Račun ili lozinka nisu točni.')]"),
                    "Test Failed: Račun ili lozinka nisu točni.", wait);

            // Provjera da li je korisnik uspješno preusmjeren na /home stranicu
            if (!wait.until(ExpectedConditions.urlContains("/home"))) {
                throw new AssertionError("Test Failed: Took too long to load home page.");
            }
            System.out.println("Test Passed: Login successful."); // Poruka uspjeha
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage()); // Ispis poruke greške u slučaju neuspjeha
        }
    }

    @AfterClass
    public void tearDown() {
        // Zatvaranje preglednika nakon završetka testa
        if (driver != null) {
            driver.quit();
        }
    }

    // Metoda za unos teksta u element na stranici
    private void enterText(By locator, String text, WebDriverWait wait) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); // Čekanje da element postane vidljiv
        element.sendKeys(text); // Unos teksta
    }

    // Metoda za klik na element na stranici
    private void clickElement(By locator, WebDriverWait wait) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator)); // Čekanje da element postane klikabilan
        element.click(); // Klik na element
    }

    // Metoda za provjeru greške na temelju prisutnosti određenog elementa
    private void checkForError(By locator, String errorMessage, WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); // Čekanje da se greška prikaže
            throw new AssertionError(errorMessage); // Bacanje greške s odgovarajućom porukom
        } catch (Exception ignored) {
            // Ako greška nije pronađena, ignorira se
        }
    }
}
