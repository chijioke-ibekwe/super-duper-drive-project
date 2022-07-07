package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(id = "go-to-signup")
    private WebElement signUpLink;

    @FindBy(id = "header")
    private WebElement loginHeader;

    private WebDriver driver;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public HomePage Login(String username,  String password, WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();

        return new HomePage(driver);
    }

    public SignupPage GoToSignup(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(signUpLink));
        signUpLink.click();

        return new SignupPage(driver);
    }
}
