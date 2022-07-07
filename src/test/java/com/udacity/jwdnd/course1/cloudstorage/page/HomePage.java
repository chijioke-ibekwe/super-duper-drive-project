package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "note-save")
    private WebElement noteSaveButton;

    @FindBy(css = "#userTable tbody tr")
    private WebElement noteRow;

    @FindBy(css = "#userTable tbody tr:last-of-type button")
    private WebElement noteEditButton;

    @FindBy(css = "#userTable tbody tr:last-of-type a")
    private WebElement noteDeleteButton;

    @FindBy(css = "#userTable tbody tr:last-of-type th")
    private WebElement titleDisplay;

    @FindBy(css = "#userTable tbody tr:last-of-type td:nth-of-type(2)")
    private WebElement descriptionDisplay;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "credential-save-button")
    private WebElement credentialSaveButton;

    @FindBy(css = "#credentialTable tbody tr")
    private WebElement credentialRow;

    @FindBy(css = "#credentialTable tbody tr:last-of-type button")
    private WebElement credentialEditButton;

    @FindBy(css = "#credentialTable tbody tr:last-of-type a")
    private WebElement credentialDeleteButton;

    @FindBy(css = "#credentialTable tbody tr:last-of-type th")
    private WebElement urlDisplay;

    @FindBy(css = "#credentialTable tbody tr:last-of-type td:nth-of-type(2)")
    private WebElement usernameDisplay;

    @FindBy(css = "#credentialTable tbody tr:last-of-type td:nth-of-type(3)")
    private WebElement passwordDisplay;

    @FindBy(css = "#logoutDiv button")
    private WebElement logoutButton;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public List<String> createNote(String title, String description, WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(notesTab));
        notesTab.click();

        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));

        addNoteButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(noteTitleField));

        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        noteSaveButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(notesTab));

        notesTab.click();

        wait.until(ExpectedConditions.visibilityOf(titleDisplay));
        List<String> results = new ArrayList<>();
        results.add(titleDisplay.getText());
        results.add(descriptionDisplay.getText());

        return results;
    }

    public String editNote(String description, WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(noteEditButton));

        noteEditButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionField));
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys(description);
        noteSaveButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(notesTab));

        notesTab.click();

        wait.until(ExpectedConditions.visibilityOf(descriptionDisplay));
        return descriptionDisplay.getText();
    }

    public void deleteNote(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(noteDeleteButton));

        noteDeleteButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(notesTab));
        notesTab.click();

        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
    }

    public List<String> createCredential(String url, String username, String password, WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab));
        credentialsTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(addCredentialButton));
        addCredentialButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrlField));
        credentialUrlField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.sendKeys(password);
        credentialSaveButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab));
        credentialsTab.click();
        wait.until(ExpectedConditions.visibilityOf(passwordDisplay));

        List<String> results = new ArrayList<>();
        results.add(urlDisplay.getText());
        results.add(usernameDisplay.getText());
        results.add(passwordDisplay.getText());

        return results;
    }

    public List<String> editCredential(String username, WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab));
        credentialsTab.click();

        wait.until(ExpectedConditions.visibilityOf(passwordDisplay));
        String encryptedPassword = passwordDisplay.getText();
        String oldUsername = usernameDisplay.getText();
        credentialEditButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordField));
        String unencryptedPassword = credentialPasswordField.getText();
        credentialUsernameField.clear();
        credentialUsernameField.sendKeys(username);
        credentialSaveButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab));
        credentialsTab.click();
        List<String> results = new ArrayList<>();
        results.add(encryptedPassword);
        results.add(unencryptedPassword);
        results.add(oldUsername);
        results.add(usernameDisplay.getText());
        return results;
    }

    public void deleteCredential(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab));
        credentialsTab.click();

        wait.until(ExpectedConditions.elementToBeClickable(credentialDeleteButton));
        credentialDeleteButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab));
        credentialsTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(addCredentialButton));
    }

    public LoginPage logout(){
        logoutButton.click();

        return new LoginPage(driver);
    }
}
