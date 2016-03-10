package com.backbase.uitest;

import com.backbase.uitests.pages.LandingBackbase;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Title;

import static com.backbase.conditions.BackbaseCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;

public class BackbaseExtranetTest extends BaseTest {
    private static final String companyUserName = "mtv1@test.com";
    private static final String companyUserPassword = "password1";
    private static final String extermalUserName = "catbug@mailinator.com";
    private static final String extermalUserPassword = "ouinkouink00";
    private static final String internalUserName = "extranet";
    private static final String internalUserPassword = "extranet_1712";
    private static final String partnerUserName = "mtv2@test.com";
    private static final String partnerUserPassword = "password1";
    private static final String supportUserName = "supporter";
    private static final String supportUserPassword = "extranet1";
    private static final String trainingUserName = "trainingteam";
    private static final String trainingUserPassword = "extranet1";
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

    @Title("Company user (partner or client")
    @Test
    public void testCompanyUserShouldLogin() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(companyUserName, companyUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(companyUserName));
    }

    @Title("Login : Login error")
    @Test
    public void testLoginShouldFail() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getLoginLink().click();
        landingBackbase.getLogInBtn().click();
        landingBackbase.getLoginForm().shouldHave(hasText("This field is required."));
        landingBackbase.getUserNameField().setValue("123");
        landingBackbase.getPasswordField().setValue("123");
        landingBackbase.getLogInBtn().click();
        landingBackbase.getLoginForm().shouldHave(hasText("The Username or Password you entered is not correct."));
        landingBackbase.getForgotPasswordLink().click();
        landingBackbase.getForgotPasswordForm().has(visible);
        landingBackbase.getForgotPasswordFormEmailField().has(enabled);
        landingBackbase.getForgotPasswordFormEmailField().click();
        landingBackbase.getForgotPasswordFormEmailField().setValue("test@ukr.net");
        landingBackbase.getForgotPasswordFormRestoreBtn().click();
        landingBackbase.getForgotPasswordForm().shouldHave(hasText("No user data found for test@ukr.net"));
    }

    @Title("Login : External user (not a partner/company, not internal")
    @Test
    public void testExternalUserShouldLogin() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(extermalUserName, extermalUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(extermalUserName));
    }

    @Title("Login : Internal user (@backbase address)")
    @Test
    public void testInternalUserShouldLogin() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(internalUserName));
    }

    @Title("Login : Company user (partner or client)")
    @Test
    public void testPartnerUserShouldLogin() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(partnerUserName, partnerUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(partnerUserName));
    }

    @Title("Login : Support user")
    @Test
    public void testSupportUserShouldLogin() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(supportUserName, supportUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(supportUserName));
    }

    @Title("Login : Training user")
    @Test
    public void testTrainingUserShouldLogin() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(trainingUserName, trainingUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(trainingUserName));
    }

    @Title("Logout elements accessibility")
    @Test
    public void testLogoutView() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getLoginLink().shouldBe(visible);
        landingBackbase.getSignUpLink().shouldBe(visible);
        landingBackbase.getSpanDisplayName().shouldNotBe(present);
        assertTrue("Page URL doesn't contain '/home' !", getWebDriver().getCurrentUrl().contains(baseUrl + "/home"));
    }

    @Title("AUTH : Navigation between login, signup, reset password tabs")
    @Test
    public void testTabsNavigation() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getSignUpLink().click();
        landingBackbase.getLogInHereLink().click();
        assertTrue("Page URL doesn't contain '/home' !", getWebDriver().getCurrentUrl().contains("/home#login"));
        landingBackbase.getSignUpInHereLink().click();
        assertTrue("Page URL doesn't contain '/home' !", getWebDriver().getCurrentUrl().contains("/home#sign-up"));
    }

    //TODO Maybe, add new widgets in DEMOs content?
    @Title("DEMOS : Videos Content - Availability depending on user type")
    @Test
    public void testDemosForInnternalUsers() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getDemosLink().hover();
        landingBackbase.getDemosLinkVideoSection().click();
        assertTrue("Title of page should be 'Videos - My Backbase', but it's: " + title(), title().contains("Videos - My Backbase"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Home"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Demos"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Videos"));
        assertTrue(landingBackbase.getVideoSections().stream().anyMatch(t -> t.has(hasText("Backbase Connect Internal Keynote"))));
        assertTrue(landingBackbase.getVideoSections().stream().anyMatch(t -> t.has(hasText("CXP Mobile SDK"))));
        assertTrue(landingBackbase.getVideoSections().stream().anyMatch(t -> t.has(hasText("URL to State Session"))));
    }

    @Title("DEMOS : Videos UI")
    @Test
    public void testDemoUIForInternalUsers() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getDemosLink().hover();
        landingBackbase.getDemosLinkVideoSection().click();
        assertTrue(title().contains("Videos - My Backbase"));
        assertTrue(landingBackbase.getVideosPictureSection().stream().allMatch(t -> t.has(containsSubElement("img"))));
        assertTrue(landingBackbase.getVideosTextSection().stream().allMatch(t -> t.has(containsSubElements("h3", "p", "em"))));
        assertTrue(landingBackbase.getWatchConferenceButton().size() > 0);
        landingBackbase.getWatchConferenceButton().get(0).click();
        landingBackbase.getVideoContainer().shouldBe(visible);
        landingBackbase.getVideoContainer().shouldHave(containsSubElementByXpath("/descendant::button[@title='Close']"));
        landingBackbase.getVideoContainerPlayButton().shouldBe(present);
    }


}
