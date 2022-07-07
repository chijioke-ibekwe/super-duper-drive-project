package com.udacity.jwdnd.course1.cloudstorage.mytest;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorizationTest {

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
    void beforeEach(){
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        homePage = new HomePage(driver);
    }

    @AfterEach
    void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void signUpAndLoginTest(){
        driver.get("http://localhost:" + this.port + "/login");
        loginPage.GoToSignup(driver);
        signupPage.Signup("John", "Doe", "john-doe", "iloveyou", driver);
        loginPage.Login("john-doe", "iloveyou", driver);

        Assertions.assertEquals(driver.getTitle(), "Home");

        homePage.logout();

        Assertions.assertEquals(driver.getTitle(), "Login");
    }

    @Test
    public void homePageAccessRestrictionTest(){
        driver.get("http://localhost:" + this.port + "/login");
        loginPage.GoToSignup(driver);
        driver.get("http://localhost:" + this.port + "/home");

        Assertions.assertNotEquals(driver.getTitle(), "Home");
        Assertions.assertEquals(driver.getTitle(), "Login");
    }

}
