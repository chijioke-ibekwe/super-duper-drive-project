package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "buttonSignUp")
    private WebElement signUpButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(id = "success-msg")
    private WebElement successfulSignup;

    private WebDriver driver;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public LoginPage Signup(String firstName, String lastName, String username, String password, WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        signUpButton.click();

        return new LoginPage(driver);
    }
}
