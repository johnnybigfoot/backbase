package com.backbase.uitests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

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
    @FindBy(xpath = "//span[@class='display-name']")
    private SelenideElement spanDisplayName;
    @FindBy(xpath = "//a[@href='#login' and text()='Log In Here!']")
    private SelenideElement logInHereLink;
    @FindBy(xpath = "//a[@href='#sign-up' and text()='Sign Up here!']")
    private SelenideElement signUpInHereLink;
    @FindBy(xpath = "//a[@role='button' and @href='/demos']")
    private SelenideElement demosLink;
    @FindBy(xpath = "//a[contains(text(),'Demos') and @class='nav-disabled'][1]")
    private SelenideElement demosLinkForUnlogged;
    @FindBy(xpath = "//ul[@class='nav navbar-nav']")
    private SelenideElement navBar;
    @FindBy(xpath = "//a[@href='/backbase-showcase']")
    private SelenideElement showcaseLink;
    @FindBy(xpath = "//a[@href='/backbase-demo']")
    private SelenideElement bachbaseDemoSublink;
    @FindBy(xpath = "//a[@href='/demos/videos' and text()='Videos']")
    private SelenideElement demosLinkVideoSection;
    @FindBy(xpath = "//ul[@class='bd-breadcrumb breadcrumb']/li")
    private ElementsCollection currentSectionMarks;
    @FindBy(xpath = "//a[@href='/docs' and @role='button']")
    private SelenideElement docsLink;
    @FindBy(xpath = "//div[contains(@class,'bb-widget-common-content')]")
    private ElementsCollection videoSections;

    public void login(String username, String pass, boolean stayLoggedIn) {
        if (!userNameField.has(Condition.visible)) loginLink.click();
        userNameField.sendKeys(username);
        passwordField.sendKeys(pass);
        rememberMeCheckBox.setSelected(stayLoggedIn);
        logInBtn.click();
    }

    public ElementsCollection findElementsAmongAllFrames(String elemXpath) {
        switchTo().defaultContent();
        ElementsCollection frames = $$(By.tagName("iframe"));
        for (int i = 0; i < frames.size() - 1; i++) {
            List<WebElement> res = switchTo().frame(i).findElements(By.xpath(elemXpath));
            ElementsCollection elems = $$(By.xpath(elemXpath));
            if (elems.size() > 0) {
                return elems;
            }
        }
        return null;
    }

    public boolean isDemoWidgetContainsText(String text) {
        for (SelenideElement element : videoSections) {
            if (element.has(Condition.hasText(text))) return true;
        }
        return false;
    }
}
