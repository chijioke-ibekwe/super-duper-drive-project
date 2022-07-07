package com.udacity.jwdnd.course1.cloudstorage.mytest;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private static LoginPage loginPage;

    private static SignupPage signupPage;

    private static HomePage homePage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        homePage = new HomePage(driver);
        driver.get("http://localhost:" + this.port + "/login");
        loginPage.GoToSignup(driver);
        signupPage.Signup("John", "Doe", "john-doe", "iloveyou", driver);
        loginPage.Login("john-doe", "iloveyou", driver);
    }


    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void credentialCreateTest(){
        String urlEntered = "www.udacity.com";
        String usernameEntered = "john-doe";
        String passwordEntered = "iloveyou";
        List<String> storedValues = homePage.createCredential(urlEntered, usernameEntered, passwordEntered, driver);

        Assertions.assertEquals(storedValues.get(0), urlEntered);
        Assertions.assertEquals(storedValues.get(1), usernameEntered);
        Assertions.assertNotEquals(storedValues.get(2), passwordEntered);
    }

    @Test
    public void credentialEditTest(){
        homePage.createCredential("www.udacity.com", "john-doe", "iloveyou", driver);
        List<String> results = homePage.editCredential("jane-doe", driver);

        Assertions.assertNotEquals(results.get(0), results.get(1));
        Assertions.assertNotEquals(results.get(2), results.get(3));
    }

    @Test
    public void credentialDeleteTest() {
        homePage.createCredential("www.udacity.com", "john-doe", "iloveyou", driver);
        List<WebElement> initialNoOfRows = driver.findElements(By.cssSelector("#credentialTable tbody tr"));
        homePage.deleteCredential(driver);
        List<WebElement> finalNoOfRows = driver.findElements(By.cssSelector("#credentialTable tbody tr"));

        Assertions.assertEquals(initialNoOfRows.size()-1, finalNoOfRows.size());
    }
}
