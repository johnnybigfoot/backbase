package com.backbase.uitests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

/**
 * Created by BigFoot on 09.03.2016.
 */
@Getter
public class DemoSection extends LandingBackbase {
    @FindBy(xpath = "//div[@class='widget-request-demo-button pull-right']/button[@class='btn btn-primary' and text()='Request Live Demo']")
    private SelenideElement requestLiveDemoBtn;
    @FindBy(xpath = "//button[contains(text(),'Download')]")
    private SelenideElement downloadBtn;
    @FindBy(css = "div.bp-container.lp-lightbox-container.panel-chrome-default.lp-lightbox-on")
    private SelenideElement requestLiveDemoForm;
    @FindBy(xpath = "//div[contains(@class,'bb-widget-common-content')]")
    private ElementsCollection videoSections;
    @FindBy(xpath = "//div[@class='bd-imageContent-dropArea image-align-center border']")
    private ElementsCollection videosPictureSection;
    @FindBy(xpath = "//div[contains(@class,'bb-widget-common-content')]/div[@class='bd-textContent-dropArea bd-ContentTemplate-para bd-contentArea']")
    private ElementsCollection videosTextSection;
    @FindBy(css = ".btn.Default")
    private ElementsCollection watchConferenceButtons;
    @FindBy(xpath = "//div[@class='bp-container lp-lightbox-container panel-chrome-default youtube-widget-container lp-lightbox-on']/div[@class='lp-lightbox-inner panel panel-chrome-default']")
    private SelenideElement activeVideoContainer;
    @FindBy(css = "div.html5-video-player.unstarted-mode.ytp-hide-controls")
    private SelenideElement videoContainerPlayButton;
    @FindBy(xpath = "//button[@class='lp-widget-control lp-lightbox-close' and @title='Close']")
    private SelenideElement videoContainerCloseButton;
    @FindBy(css = "div.lp-lightbox-overlay")
    private ElementsCollection lightBoxOverlays;
    @FindBy(xpath = "//*[text()='Backbase Showcase Installer']")
    private SelenideElement showcaseInstaller;
    @FindBy(xpath = "//*[text()='Backbase Showcase Archetype']")
    private SelenideElement showcaseArchetype;
    @FindBy(css = "div.toast-message")
    private SelenideElement messageSection;

    public boolean isDemoWidgetContainsText(String text) {
        for (SelenideElement element : videoSections) {
            if (element.has(Condition.hasText(text))) return true;
        }
        return false;
    }


}
