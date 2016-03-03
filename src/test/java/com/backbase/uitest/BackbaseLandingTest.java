package com.backbase.uitest;

import com.backbase.uitests.pages.LandingBackbase;
import com.codeborne.selenide.junit.ScreenShooter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class BackbaseLandingTest extends BaseTest {
    @Rule
    public ScreenShooter makeScreenshotOnFailure = ScreenShooter.failedTests();

    @Before
    public void overrideSelenideConfig() {
        timeout = driverTimout * 1000;
        baseUrl = backbaseUrl;
        startMaximized = true;
        holdBrowserOpen = false;
        browser = driver;
//        remote = "http://localhost:4444/wd/hub";
        clearBrowserCache();
    }

    @Test
    public void testLogin() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
//      $(By.name("q")).val("selenide").pressEnter();
//    $$("#ires .g").shouldHave(size(10));
//    $("#ires .g").shouldHave(text("selenide.org"));
        landingBackbase.login("sadf", "asdf", false);
    }

    @Test
    public void test2() {
        open("/home");
    }
}
