package com.backbase.uitests.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by eugene.salmin on 2/02/2016.
 */
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

    public void login(String username, String pass, boolean stayLoggedIn) {
        loginLink.click();
        userNameField.sendKeys(username);
        passwordField.sendKeys(pass);
        rememberMeCheckBox.setSelected(stayLoggedIn);
        logInBtn.click();
    }
}
