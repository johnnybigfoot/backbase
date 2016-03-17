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
    @FindBy(id = "bb2doc-previouslink")
    private SelenideElement previousPageBtn;
    @FindBy(id = "bb2doc-nextlink")
    private SelenideElement nextPageBtn;
    @FindBy(xpath = "//div[@class='announcement-item']")
    private ElementsCollection annnouncementItems;
    @FindBy(xpath = "//*[@id='bb2doc-pdf']/a")
    private SelenideElement downloadAllAsPdfBtn;
    @FindBy(xpath = "//h5[@onclick='expand(this.parentNode)']")
    private SelenideElement versionInfoExpandBtn;
    @FindBy(xpath = "//div[@class='bb2doc-simplesect-content']")
    private SelenideElement expandedVersionInfo;
    @FindBy(className = "link-print-page")
    private SelenideElement printPageBtn;
    @FindBy(className = "link-scroll-to")
    private SelenideElement scrollToTopBtn;
    @FindBy(css = "div.bp-widget[data-pid=widget-docs-versions-instance]")
    private SelenideElement contentLeftPane;
    @FindBy(className = "tree")
    private ElementsCollection trees;
    @FindBy(css = ".fa-plus-circle")
    private ElementsCollection expandTreePlusMarks;
    @FindBy(css = ".fa-minus-circle")
    private ElementsCollection collapseTreeMinusMarks;
    @FindBy(css = "div.versions div:nth-child(1) div:nth-child(1) ul:nth-child(2)")
    private SelenideElement versionsTree;
    @FindBy(css = "div.active div.active h5.active")
    private SelenideElement activeGreenSection;
    @FindBy(xpath = "//div[@class='active']/ul")
    private SelenideElement subnavigationSection;

    public SelenideElement getNeededDocumentationButton (String sectionTitle) {
        return $(By.xpath("//h3[@class='bd-textContent-dropArea bd-ContentTemplate-para bd-contentArea bd-notEditable' and text()='" + sectionTitle + "']/parent::div/div/div/a"));
    }
}
