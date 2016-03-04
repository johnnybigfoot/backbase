package com.backbase.uitest;

import com.backbase.uitests.pages.LandingBackbase;
import com.codeborne.selenide.Condition;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class BackbaseExtranetTest extends BaseTest {
    private static final String companyUserName = "mtv1@test.com";
    private static final String companyUserPassword = "password1";

//    @Rule
//    public ScreenShooter makeScreenshotOnFailure = ScreenShooter.failedTests();

    @Before
    public void overrideSelenideConfig() {
        timeout = driverTimeoutInSeconds * 1000;
        baseUrl = backbaseUrl;
        startMaximized = true;
        holdBrowserOpen = false;
        browser = browserName;
//        browser = "chrome";
//        remote = "http://localhost:4444/wd/hub";
        clearBrowserCache();
    }

    @Test
         public void testCompanyUserShouldLogin() {
//      $(By.name("q")).val("selenide").pressEnter();
//    $$("#ires .g").shouldHave(size(10));
//    $("#ires .g").shouldHave(text("selenide.org"));
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(companyUserName, companyUserPassword, false);
        landingBackbase.getUserProfileMenu().has(Condition.visible);
    }

    @Test
    public void testLoginShouldFail() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getLoginLink().click();
        landingBackbase.getLogInBtn().click();
        landingBackbase.getLoginForm().shouldHave(Condition.hasText("This field is required."));
        landingBackbase.getUserNameField().setValue("123");
        landingBackbase.getPasswordField().setValue("123");
        landingBackbase.getLogInBtn().click();
        landingBackbase.getLoginForm().shouldHave(Condition.hasText("The Username or Password you entered is not correct."));
        landingBackbase.getForgotPasswordLink().click();
        landingBackbase.getForgotPasswordForm().has(Condition.visible);
        landingBackbase.getForgotPasswordFormEmailField().has(Condition.enabled);
        landingBackbase.getForgotPasswordFormEmailField().click();
        landingBackbase.getForgotPasswordFormEmailField().setValue("test@ukr.net");
        landingBackbase.getForgotPasswordFormRestoreBtn().click();
        landingBackbase.getForgotPasswordForm().shouldHave(Condition.hasText("No user data found for test@ukr.net"));
    }
}
