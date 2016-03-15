package com.backbase.uitests.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

/**
 * Created by BigFoot on 14.03.2016.
 */
@Getter
public class RequestDemoForm {
    @FindBy(id = "firstName")
    private SelenideElement firstNameField;
    @FindBy(id = "lastName")
    private SelenideElement lastNameField;
    @FindBy(id = "company")
    private SelenideElement companyField;
    @FindBy(id = "country")
    private SelenideElement countrySelect;
    @FindBy(id = "title")
    private SelenideElement titleField;
    @FindBy(id = "email")
    private SelenideElement emailField;
    @FindBy(id = "telephoneNumber")
    private SelenideElement phoneField;
    @FindBy(xpath = "//select[@id='description' and @class='required']")
    private SelenideElement descriptionSelect;
    @FindBy(xpath = "//form[@class='mbb-form request-demo-form']/button")
    private SelenideElement requestLiveDemoBtn;
    @FindBy(tagName = "label")
    private ElementsCollection labels;
    @FindBy(xpath = "//*[@id='terms']")
    private SelenideElement acceptTermcCheckbox;
}