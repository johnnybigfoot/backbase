package com.backbase.uitests.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

/**
 * Created by eugene.salmin on 2/02/2016.
 */
@Getter
public class LandingBackbase {
    @FindBy(xpath = "//a[contains(@class, 'signup-button')]")
    private SelenideElement signUpLink;
    @FindBy(css = ".login-link")
    private SelenideElement loginLink;
    @FindBy(css = "#j_username")
    private SelenideElement userNameField;
    @FindBy(css = "#j_password")
    private SelenideElement passwordField;
    @FindBy(css = "#remember-me")
    private SelenideElement rememberMeCheckBox;
    @FindBy(xpath = "//button[@class='btn btn-primary' and contains(text(), 'Log In')]")
    private SelenideElement logInBtn;
    @FindBy(css = ".user-profile-mini")
    private SelenideElement userProfileSection;
    @FindBy(css = ".mbb-widget-login-form")
    private SelenideElement loginForm;
    @FindBy(xpath = "//a[@class='user-profile-mini-display dropdown-toggle']")
    private SelenideElement userProfileMenu;
    @FindBy(css = "label.error")
    private ElementsCollection errorLabel;
    @FindBy(xpath = "//p[@class='have-account']/a[@href='#forgot-password']")
    private SelenideElement forgotPasswordLink;
    @FindBy(xpath = "//div[@class='default bp-container bb-simple-tabs-container bb-simple-tabs-container-4']")
    private SelenideElement forgotPasswordForm;
    @FindBy(xpath = "//h2[text()='Reset Password']/following-sibling::input[@id='email']")
    private SelenideElement forgotPasswordFormEmailField;
    @FindBy(xpath = "//button[@class='btn btn-primary' and text()='Restore']")
    private SelenideElement forgotPasswordFormRestoreBtn;

    public void login(String username, String pass, boolean stayLoggedIn) {
        loginLink.click();
        userNameField.sendKeys(username);
        passwordField.sendKeys(pass);
        rememberMeCheckBox.setSelected(stayLoggedIn);
        logInBtn.click();
    }
}
