package com.backbase.uitests.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by BigFoot on 09.03.2016.
 */
@Getter
public class DocsSection extends LandingBackbase {
    @FindBy(xpath = "//div[@class='announcement-item']")
    private ElementsCollection annnouncementItems;

    public SelenideElement getNeededDocumentationButton(String sectionTitle) {
        return $(By.xpath("//h3[@class='bd-textContent-dropArea bd-ContentTemplate-para bd-contentArea bd-notEditable' and text()='" + sectionTitle + "']/parent::div/div/div/a"));
    }
}
