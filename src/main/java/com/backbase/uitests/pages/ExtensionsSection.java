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
public class ExtensionsSection extends LandingBackbase {
    @FindBy(css = "div.container.carousel-outer")
    private SelenideElement carousel;
    @FindBy(css = "div.widget-extensions-nav-tiles")
    private SelenideElement navTilesWidget;
    @FindBy(css = "li.sr-nav-link-wrapper > div.tile-wrapper")
    private ElementsCollection navTiles;
    @FindBy(xpath = "//div[@class='widget-extensions-nav-tiles']/div/ul/li/div/a/img")
    private ElementsCollection navTileImages;
    @FindBy(xpath = "//div[@class='widget-extensions-nav-tiles']/div/ul/li/div/a/h3")
    private ElementsCollection navTileHeaders;
    @FindBy(xpath = "//div[@class='widget-extensions-nav-tiles']/div/ul/li/div/a/h5")
    private ElementsCollection navTileSubtitles;
    @FindBy(xpath = "//div[@class='widget-extensions-nav-tiles']/div/ul/li/div/a/p")
    private ElementsCollection navTileDescr;
    @FindBy(css = "div.tile-wrapper > a.sr-nav-link")
    private ElementsCollection navTileContents;
    @FindBy(css = "div.widget-extensions-details")
    private SelenideElement extensionDetailsWidget;
    @FindBy(xpath = "//div[@class='bd-textContent-dropArea bd-ContentTemplate-para bd-contentArea']/h3[text()='HOW TO']")
    private SelenideElement howToSection;

    public SelenideElement getBanner(int bannerIndex) {
        return $(By.cssSelector("div.owl-item:nth-child(" + bannerIndex + ") > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1)"));
    }
}
