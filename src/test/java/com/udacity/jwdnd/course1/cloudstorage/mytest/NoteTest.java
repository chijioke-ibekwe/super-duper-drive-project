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
class NoteTest {

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
    public void noteCreateTest(){
        String titleEntered = "Day 1";
        String descriptionEntered = "I am very happy";
        List<String> storedValues = homePage.createNote(titleEntered, descriptionEntered, driver);

        Assertions.assertEquals(storedValues.get(0), titleEntered);
        Assertions.assertEquals(storedValues.get(1), descriptionEntered);
    }

    @Test
    public void noteEditTest(){
        List<String> results = homePage.createNote("Day 1", "I am very happy", driver);
        String newDescriptionEntered = "I am very happy because today is Friday";
        String storedDescription = homePage.editNote(newDescriptionEntered, driver);

        Assertions.assertEquals(storedDescription, newDescriptionEntered);
        Assertions.assertNotEquals(storedDescription, results.get(1));
    }

    @Test
    public void noteDeleteTest() {
        homePage.createNote("Day 1", "I am very happy", driver);
        homePage.deleteNote(driver);

        Assertions.assertThrows(NoSuchElementException.class, () -> driver.findElement(By.cssSelector("#userTable tbody tr")));
    }
}
