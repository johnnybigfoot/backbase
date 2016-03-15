package com.backbase.uitest;

import com.backbase.uitests.pages.DemoSection;
import com.backbase.uitests.pages.LandingBackbase;
import com.backbase.uitests.pages.RequestDemoForm;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Title;

import static com.backbase.conditions.BackbaseCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BackbaseExtranetTest extends BaseTest {
    private static final String companyUserName = "mtv1@test.com";
    private static final String companyUserPassword = "password1";
    private static final String externalUserName = "catbug@mailinator.com";
    private static final String externalUserPassword = "ouinkouink00";
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
    public void overrideSelenideConfig () {
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
    public void testCompanyUserShouldLogin () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(companyUserName, companyUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(companyUserName));
    }

    @Title("Login : Login error")
    @Test
    public void testLoginShouldFail () {
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
    public void testExternalUserShouldLogin () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(externalUserName, externalUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(externalUserName));
    }

    @Title("Login : Internal user (@backbase address)")
    @Test
    public void testInternalUserShouldLogin () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(internalUserName));
    }

    @Title("Login : Company user (partner or client)")
    @Test
    public void testPartnerUserShouldLogin () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(partnerUserName, partnerUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(partnerUserName));
    }

    @Title("Login : Support user")
    @Test
    public void testSupportUserShouldLogin () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(supportUserName, supportUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(supportUserName));
    }

    @Title("Login : Training user")
    @Test
    public void testTrainingUserShouldLogin () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(trainingUserName, trainingUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(trainingUserName));
    }

    @Title("Logout elements accessibility")
    @Test
    public void testLogoutView () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getLoginLink().shouldBe(visible);
        landingBackbase.getSignUpLink().shouldBe(visible);
        landingBackbase.getSpanDisplayName().shouldNotBe(present);
        assertTrue("Page URL doesn't contain '/home' !", getWebDriver().getCurrentUrl().contains("/home"));
    }

    @Title("AUTH : Navigation between login, signup, reset password tabs")
    @Test
    public void testTabsNavigation () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getSignUpLink().click();
        landingBackbase.getLogInHereLink().click();
        assertTrue("Page URL doesn't contain '/home' !", getWebDriver().getCurrentUrl().contains("home#login"));
        landingBackbase.getSignUpInHereLink().click();
        assertTrue("Page URL doesn't contain '/home' !", getWebDriver().getCurrentUrl().contains("home#sign-up"));
    }

    //TODO Maybe, add new widgets in DEMOs content?
    @Title("DEMOS : Videos Content - Availability depending on user type")
    @Test
    public void testDemosForInnternalUsers () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getDemosLink().hover();
        landingBackbase.getDemosLinkVideoSection().click();
        assertTrue("Title of page should be 'Videos - My Backbase', but it's: " + title(), title().contains("Videos - My Backbase"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Home"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Demos"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Videos"));
        DemoSection demoSection = page(DemoSection.class);
        assertTrue(demoSection.getVideoSections().stream().anyMatch(t -> t.has(hasText("Backbase Connect Internal Keynote"))));
        assertTrue(demoSection.getVideoSections().stream().anyMatch(t -> t.has(hasText("CXP Mobile SDK"))));
        assertTrue(demoSection.getVideoSections().stream().anyMatch(t -> t.has(hasText("URL to State Session"))));
    }

    @Title("DEMOS : Videos UI")
    @Test
    public void testDemoUIForInternalUsers () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getDemosLink().hover();
        landingBackbase.getDemosLinkVideoSection().click();
        assertTrue(title().contains("Videos - My Backbase"));
        DemoSection demoSection = page(DemoSection.class);
        assertTrue(demoSection.getVideosPictureSection().stream().allMatch(t -> t.has(containsSubElement("img"))));
        assertTrue(demoSection.getVideosTextSection().stream().allMatch(t -> t.has(containsSubElements("h3", "p", "em"))));
        demoSection.getWatchConferenceButtons().get(0).click();
        assertTrue(demoSection.getWatchConferenceButtons().size() > 0);
        demoSection.getActiveVideoContainer().shouldHave(containsSubElementByXpath("/descendant::button[@title='Close']"));
        ElementsCollection playButtonCollection = demoSection.findElementsAmongAllFrames("/descendant::button[@class='ytp-large-play-button ytp-button']");
        assertTrue(playButtonCollection != null);
        switchTo().defaultContent();  //here's some trick. You have to switch to default content often, otherwise your locators won;t work
        demoSection.getActiveVideoContainer().$(By.xpath("/descendant::button[@title='Close']")).click();
        demoSection.getActiveVideoContainer().shouldNotBe(visible);
        demoSection.getWatchConferenceButtons().get(0).click();
        demoSection.getActiveVideoContainer().shouldHave(containsSubElementByXpath("/descendant::button[@title='Close']"));
        demoSection.getLightBoxOverlays().get(0).click();
        demoSection.getActiveVideoContainer().shouldNotBe(visible);
        assertTrue(demoSection.getVideosTextSection().stream().anyMatch(t -> t.has(text("URL to State Session"))));
        assertTrue(demoSection.getVideosTextSection().stream().anyMatch(t -> t.has(text("CXP Mobile SDK"))));
        assertTrue(demoSection.getVideosTextSection().stream().anyMatch(t -> t.has(text("Developing with Launchpad"))));
    }

    @Title("DEMOS : external user (not a partner, not internal)")
    @Test
    public void testDemoUIForExternalUsers () {
        LandingBackbase landingBackbase = open(baseUrl + "backbase-demo", LandingBackbase.class);
        assertTrue("Page URL doesn't contain '/login-register' !", getWebDriver().getCurrentUrl().contains("login-register"));
        landingBackbase.getDemosLinkForUnlogged().hover();
        landingBackbase.getDemosLinkVideoSection().shouldNotBe(present);
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getShowcaseLink().shouldBe(visible);
        landingBackbase.getShowcaseLink().click();
        landingBackbase.login(externalUserName, externalUserPassword, false);
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Home"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Demos"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Backbase Showcase"));
        DemoSection demoSection = page(DemoSection.class);
        assertTrue("Title of page should be 'Backbase Showcase - My Backbase', but it's: " + title(), title().contains("Backbase Showcase - My Backbase"));
        demoSection.getRequestLiveDemoBtn().shouldBe(visible);
        demoSection.getDownloadBtn().shouldNotBe(present);
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
        demoSection.getShowcaseInstaller().shouldNotBe(present);
        demoSection.getShowcaseArchetype().shouldNotBe(present);
        assertFalse(demoSection.isDemoWidgetContainsText("Backbase Mobile Demo for iOS"));
        demoSection.getRequestLiveDemoBtn().click();
        demoSection.getRequestLiveDemoForm().shouldBe(visible);
        RequestDemoForm requestDemoForm = page(RequestDemoForm.class);
        requestDemoForm.getFirstNameField().setValue("Cat");
        requestDemoForm.getLastNameField().setValue("Bug");
        requestDemoForm.getCompanyField().setValue("Braviest Warriors");
        requestDemoForm.getTitleField().setValue("Pet");
        requestDemoForm.getEmailField().setValue("catbug@mailinator.com");
        requestDemoForm.getPhoneField().setValue("00000000000");
        requestDemoForm.getRequestLiveDemoBtn().click();
        requestDemoForm.getCountrySelect().selectOption("Peru");
        requestDemoForm.getLabels().stream().anyMatch(t -> t.has(text("This field is required.")));
        requestDemoForm.getDescriptionSelect().selectOption("Client");
        requestDemoForm.getRequestLiveDemoBtn().click();
        requestDemoForm.getLabels().stream().anyMatch(t -> t.has(text("This field is required.")));
        requestDemoForm.getAcceptTermcCheckbox().setSelected(true);
        requestDemoForm.getRequestLiveDemoBtn().click();
        demoSection.getMessageSection().shouldHave(text("Thank you for your Evaluation Request, we will contact you within 48 hours."));
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
    }

    @Title("DEMOS : external user (not a partner, not internal)")
    @Test
    public void testDemoUIForExternalUsersForm () {
        LandingBackbase landingBackbase = open(baseUrl + "backbase-demo", LandingBackbase.class);
        assertTrue("Page URL doesn't contain '/login-register' !", getWebDriver().getCurrentUrl().contains("login-register"));
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getShowcaseLink().click();
        landingBackbase.login(externalUserName, externalUserPassword, false);
        DemoSection demoSection = page(DemoSection.class);
        demoSection.getRequestLiveDemoBtn().click();
        demoSection.getRequestLiveDemoForm().shouldBe(visible);
        RequestDemoForm requestDemoForm = page(RequestDemoForm.class);
        requestDemoForm.getFirstNameField().setValue("");
        requestDemoForm.getLastNameField().setValue("");
        requestDemoForm.getCountrySelect().selectOption("Select Country");
        requestDemoForm.getCompanyField().setValue("");
        requestDemoForm.getTitleField().setValue("");
        requestDemoForm.getEmailField().setValue("");
        requestDemoForm.getPhoneField().setValue("");
        requestDemoForm.getDescriptionSelect().selectOption("Select Evaluation Purpose");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 9);
        requestDemoForm.getCompanyField().pressEnter();
        demoSection.getRequestLiveDemoForm().shouldBe(visible);
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 9);
        requestDemoForm.getFirstNameField().setValue("Cat");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 8);
        requestDemoForm.getLastNameField().setValue("Bug");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 7);
        requestDemoForm.getCompanyField().setValue("Braviest Warriors");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 6);
        requestDemoForm.getTitleField().setValue("Pet");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 5);
        requestDemoForm.getEmailField().setValue("catbug@mailinator.com");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 4);
        requestDemoForm.getPhoneField().setValue("00000000000");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 3);
        requestDemoForm.getCountrySelect().selectOption("Peru");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 2);
        requestDemoForm.getDescriptionSelect().selectOption("Client");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() == 1);
        requestDemoForm.getAcceptTermcCheckbox().setSelected(true);
        requestDemoForm.getRequestLiveDemoBtn().click();
        demoSection.getMessageSection().shouldHave(text("Thank you for your Evaluation Request, we will contact you within 48 hours."));
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
    }

    @Title("DEMOS : internal user (@backbase address)")
    @Test
    public void testDemoForInternalUsers () {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getDemosLink().hover();
        landingBackbase.getBachbaseDemoSublink().click();
        assertTrue("Page URL doesn't contain '/backbase-demo' !", getWebDriver().getCurrentUrl().contains("backbase-demo"));
        assertTrue("Title of page should be 'Backbase Demo - My Backbase', but it's: " + title(), title().contains("Backbase Demo - My Backbase"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Home"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Demos"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Backbase Demo"));
        DemoSection demoSection = page(DemoSection.class);
        demoSection.getDownloadBtn().shouldBe(visible);
        demoSection.getRequestLiveDemoBtn().shouldNotBe(visible);
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
        demoSection.getDemoInstallerLink().shouldBe(visible);
        demoSection.getDemoArchetypeLink().shouldBe(visible);
//        demoSection.getDownloadBtn().find(By.tagName("i")).click();
        demoSection.getDownloadBtn().click();  //TODO Make this thing stable
        demoSection.getDownloadBtnForMac().shouldBe(visible);
        demoSection.getDownloadBtnForWin().shouldBe(visible);
        demoSection.getDownloadBtn().shouldBe(visible);
        demoSection.getDownloadBtn().click();
        demoSection.getDownloadBtnForMac().shouldNotBe(visible);
        demoSection.getDownloadBtnForWin().shouldNotBe(visible);
        landingBackbase.getDemosLink().hover();
        landingBackbase.getShowcaseLink().click();
        assertTrue("Title of page should be 'Backbase Showcase - My Backbase', but it's: " + title(), title().contains("Backbase Showcase - My Backbase"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Home"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Demos"));
        landingBackbase.getCurrentSectionDiv().shouldHave(hasText("Backbase Showcase"));
        demoSection.getDownloadBtn().shouldBe(visible);
        demoSection.getRequestLiveDemoBtn().shouldNotBe(visible);
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
        demoSection.getBackbaseShowcaseInstallerLink().shouldBe(visible);
        demoSection.getBackbaseShowcaseArchetypeLink().shouldBe(visible);
        demoSection.getBackbaseMobileShowcaseLink().shouldBe(visible);
    }
}
