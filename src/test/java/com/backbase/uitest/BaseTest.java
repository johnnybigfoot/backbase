package com.backbase.uitest;

import com.backbase.uitests.pages.DemoSection;
import com.backbase.uitests.pages.DocsSection;
import com.backbase.uitests.pages.LandingBackbase;
import com.backbase.utils.Config;
import com.codeborne.selenide.SelenideElement;
import org.junit.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertTrue;

/**
 * Created by BigFoot on 02.03.2016.
 */
public class BaseTest {
    static final String companyUserName = "mtv1@test.com";
    static final String companyUserPassword = "password1";
    static final String externalUserName = "catbug@mailinator.com";
    static final String externalUserPassword = "ouinkouink00";
    static final String internalUserName = "extranet";
    static final String internalUserPassword = "extranet_1712";
    static final String partnerUserName = "mtv2@test.com";
    static final String partnerUserPassword = "password1";
    static final String supportUserName = "supporter";
    static final String supportUserPassword = "extranet1";
    static final String trainingUserName = "trainingteam";
    static final String trainingUserPassword = "extranet1";
    String backbaseUrl = Config.getProperty("baseUrl");
    int driverTimeoutInSeconds = Integer.parseInt(Config.getProperty("timeout"));
    String browserName = Config.getProperty("driver");

    @Before
    public void overrideSelenideConfig() {
        timeout = driverTimeoutInSeconds * 1000;
        baseUrl = backbaseUrl;
        startMaximized = true;
        holdBrowserOpen = false;
        browser = browserName;
//        browser = "chrome";
//        remote = "http://localhost:4444/wd/hub";
//        remote = "http://grid.backbasecloud.com:4444//wd/hub";
        clearBrowserCache();
    }

    protected void clickWithJS(SelenideElement selenideElement) {
        WebDriver driver = com.codeborne.selenide.WebDriverRunner.getWebDriver();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", selenideElement);
    }

    protected boolean isBreadcrumbContainsTexts(LandingBackbase landingBackbase, String... text) {
        String[] existingText = landingBackbase.getCurrentSectionMarks().getTexts();
        List<String> exText = new ArrayList<>();
        for (int i = 0; i < existingText.length; i++)
            exText.add(existingText[i].replaceAll("\n", "").replaceAll("»", ""));
        return exText.containsAll(Arrays.asList(text));
    }

    protected void logout() {
        open(backbaseUrl + "j_spring_security_logout?portalName=extranet");
    }

    protected void smokeCheckOfDocsSection(LandingBackbase landingBackbase, DocsSection docsSection) {
        landingBackbase.getNavBarButton("Docs").click();
        assertTrue("Page URL doesn't contain '/docs' !", url().contains("/docs"));
        docsSection.getNeededDocumentationButton("CXP").click();
        assertTrue("Page URL doesn't contain '/product-documentation/documentation' !", url().contains("/product-documentation/documentation"));
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Docs", "Product Documentation"));
    }

    protected void smokeCheckOfDownloadBtn(DemoSection demoSection) {
        while (!demoSection.getDownloadBtnForMac().is(visible)) {
            demoSection.getDownloadBtn().click();
        }
        while (demoSection.getDownloadBtnForMac().is(visible)) {
            demoSection.getDownloadBtn().click();
        }
        while (!demoSection.getDownloadBtnForWin().is(visible)) {
            demoSection.getDownloadBtn().click();
        }
        while (demoSection.getDownloadBtnForWin().is(visible)) {
            demoSection.getDownloadBtn().click();
        }
    }
}
