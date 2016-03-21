package com.backbase.uitest;

import com.backbase.conditions.PredicateTitleContains;
import com.backbase.conditions.PredicateURLContains;
import com.backbase.uitests.pages.*;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.Random;

import static com.backbase.conditions.BackbaseCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.url;
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

    private void logout() {
        open(backbaseUrl + "j_spring_security_logout?portalName=extranet");
    }

    private void smokeCheckOfDocsSection(LandingBackbase landingBackbase, DocsSection docsSection) {
        landingBackbase.getNavBarButton("Docs").click();
        assertTrue("Page URL doesn't contain '/docs' !", url().contains("/docs"));
        docsSection.getNeededDocumentationButton("CXP").click();
        assertTrue("Page URL doesn't contain '/product-documentation/documentation' !", url().contains("/product-documentation/documentation"));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Docs"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("CXP"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Documentation"))));
    }

    private void smokeCheckOfDownloadBtn(DemoSection demoSection) {
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
        landingBackbase.login(externalUserName, externalUserPassword, false);
        landingBackbase.getUserProfileMenu().shouldHave(text(externalUserName));
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
        assertTrue("Page URL doesn't contain '/home' !", url().contains("/home"));
    }

    @Title("AUTH : Navigation between login, signup, reset password tabs")
    @Test
    public void testTabsNavigation() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.getSignUpLink().click();
        landingBackbase.getLogInHereLink().click();
        assertTrue("Page URL doesn't contain '/home' !", url().contains("home#login"));
        landingBackbase.getSignUpInHereLink().click();
        assertTrue("Page URL doesn't contain '/home' !", url().contains("home#sign-up"));
    }

    //TODO Maybe, add new widgets in DEMOs content?
    @Title("DEMOS : Videos Content - Availability depending on user type")
    @Test
    public void testDemosForInternalUsers() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Videos").click();
        assertTrue("Title of page should be 'Videos - My Backbase', but it's: " + title(), title().contains("Videos - My Backbase"));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Demos"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Videos"))));
        DemoSection demoSection = page(DemoSection.class);
        assertTrue(demoSection.getVideoSections().stream().anyMatch(t -> t.has(hasText("Backbase Connect Internal Keynote"))));
        assertTrue(demoSection.getVideoSections().stream().anyMatch(t -> t.has(hasText("CXP Mobile SDK"))));
        assertTrue(demoSection.getVideoSections().stream().anyMatch(t -> t.has(hasText("URL to State Session"))));
    }

    @Title("DEMOS : Videos UI")
    @Test
    public void testDemoUIForInternalUsers() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Videos").click();
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
    public void testDemoUIForExternalUsers() {
        LandingBackbase landingBackbase = open(baseUrl + "backbase-demo", LandingBackbase.class);
        assertTrue("Page URL doesn't contain '/login-register' !", url().contains("login-register"));
        landingBackbase.getDemosLinkForUnlogged().hover();
        landingBackbase.getNavBarButton("Demos", "Videos").shouldNotBe(present);
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(externalUserName, externalUserPassword, false);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Backbase Showcase").shouldBe(visible);
        landingBackbase.getNavBarButton("Demos", "Backbase Showcase").click();
        DemoSection demoSection = page(DemoSection.class);
        demoSection.getRequestLiveDemoBtn().shouldBe(visible);
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Demos"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Backbase Showcase"))));
        assertTrue("Title of page should be 'Backbase Showcase - My Backbase', but it's: " + title(), title().contains("Backbase Showcase - My Backbase"));
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
    public void testDemoUIForExternalUsersForm() {
        LandingBackbase landingBackbase = open(baseUrl + "backbase-demo", LandingBackbase.class);
        assertTrue("Page URL doesn't contain '/login-register' !", url().contains("login-register"));
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(externalUserName, externalUserPassword, false);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Backbase Showcase").click();
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
    public void testDemoForInternalUsers() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Backbase Demo").click();
        assertTrue("Page URL doesn't contain '/backbase-demo' !", url().contains("backbase-demo"));
        assertTrue("Title of page should be 'Backbase Demo - My Backbase', but it's: " + title(), title().contains("Backbase Demo - My Backbase"));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Demos"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Backbase Demo"))));
        DemoSection demoSection = page(DemoSection.class);
        demoSection.getDownloadBtn().shouldBe(visible);
        demoSection.getRequestLiveDemoBtn().shouldNotBe(visible);
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
        demoSection.getDemoInstallerLink().shouldBe(visible);
        demoSection.getDemoArchetypeLink().shouldBe(visible);
        smokeCheckOfDownloadBtn(demoSection);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Backbase Showcase").click();
        assertTrue("Title of page should be 'Backbase Showcase - My Backbase', but it's: " + title(), title().contains("Backbase Showcase - My Backbase"));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Demos"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Backbase Showcase"))));
        demoSection.getDownloadBtn().shouldBe(visible);
        demoSection.getRequestLiveDemoBtn().shouldNotBe(visible);
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
        demoSection.getBackbaseShowcaseInstallerLink().shouldBe(visible);
        demoSection.getBackbaseShowcaseArchetypeLink().shouldBe(visible);
        demoSection.getBackbaseMobileShowcaseLink().shouldBe(visible);
    }

    @Title("DEMO : partner user")
    @Description("Part of test was removed from original DEMOS_demo_partner.html because it duplicates previous test logic - empty fields validation")
    @Test
    public void testDemoForPartnerUsers() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(partnerUserName, partnerUserPassword, false);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Backbase Demo").click();
        assertTrue("Page URL doesn't contain '/backbase-demo' !", url().contains("backbase-demo"));
        assertTrue("Title of page should be 'Backbase Demo - My Backbase', but it's: " + title(), title().contains("Backbase Demo - My Backbase"));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Demos"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Backbase Demo"))));
        DemoSection demoSection = page(DemoSection.class);
        demoSection.getDownloadBtn().shouldBe(visible);
        demoSection.getRequestLiveDemoBtn().shouldNotBe(visible);
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
        demoSection.getDemoInstallerLink().shouldBe(visible);
        demoSection.getDemoArchetypeLink().shouldBe(visible);
        demoSection.getDownloadBtn().click();  //TODO Make this thing stable
        demoSection.getDownloadBtnForMac().shouldBe(visible);
        demoSection.getDownloadBtnForWin().shouldBe(visible);
        demoSection.getDownloadBtn().shouldBe(visible);
        demoSection.getDownloadBtn().click();
        demoSection.getDownloadBtnForMac().shouldNotBe(visible);
        demoSection.getDownloadBtnForWin().shouldNotBe(visible);
        open(baseUrl, LandingBackbase.class);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Backbase Showcase").click();
        assertTrue("Title of page should be 'Backbase Showcase - My Backbase', but it's: " + title(), title().contains("Backbase Showcase - My Backbase"));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Demos"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Backbase Showcase"))));
        demoSection.getRequestLiveDemoBtn().shouldBe(visible);
        demoSection.getDownloadBtn().shouldBe(present);
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
        demoSection.getBackbaseShowcaseInstallerLink().shouldBe(visible);
        demoSection.getBackbaseShowcaseArchetypeLink().shouldNotBe(visible);
        demoSection.getBackbaseMobileShowcaseLink().shouldNot(visible);
        demoSection.getRequestLiveDemoBtn().click();
        demoSection.getRequestLiveDemoForm().shouldBe(visible);
        RequestDemoForm requestDemoForm = page(RequestDemoForm.class);
        requestDemoForm.getFirstNameField().setValue("MTV2_Partner");
        requestDemoForm.getLastNameField().setValue("Test");
        requestDemoForm.getCompanyField().setValue("Backbase");
        requestDemoForm.getTitleField().setValue("Test Partner");
        requestDemoForm.getEmailField().setValue("mtv2@test.com");
        requestDemoForm.getPhoneField().setValue("+33(000000000)");
        requestDemoForm.getCountrySelect().selectOption("Serbia");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() > 1);
        requestDemoForm.getDescriptionSelect().selectOption("Client");
        requestDemoForm.getRequestLiveDemoBtn().click();
        assertTrue(requestDemoForm.getLabels().stream().filter(l -> l.has(text("This field is required."))).count() > 0);
        requestDemoForm.getAcceptTermcCheckbox().setSelected(true);
        requestDemoForm.getRequestLiveDemoBtn().click();
        demoSection.getMessageSection().shouldHave(text("Thank you for your Evaluation Request, we will contact you within 48 hours."));
        demoSection.getRequestLiveDemoForm().shouldNotBe(visible);
    }

    @Title("DOCS : CXP Documentation UI")
    @Test
    public void testCXPDocumentationUI() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(companyUserName, companyUserPassword, false);
        landingBackbase.getNavBarButton("Docs").click();
        DocsSection docsSection = page(DocsSection.class);
        smokeCheckOfDocsSection(landingBackbase, docsSection);
        logout();
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getNavBarButton("Docs").click();
        docsSection = page(DocsSection.class);
        smokeCheckOfDocsSection(landingBackbase, docsSection);
        logout();
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(partnerUserName, partnerUserPassword, false);
        landingBackbase.getNavBarButton("Docs").click();
        docsSection = page(DocsSection.class);
        smokeCheckOfDocsSection(landingBackbase, docsSection);
        logout();
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(supportUserName, supportUserPassword, false);
        landingBackbase.getNavBarButton("Docs").click();
        docsSection = page(DocsSection.class);
        smokeCheckOfDocsSection(landingBackbase, docsSection);
        logout();
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(externalUserName, externalUserPassword, false);
        landingBackbase.getNavBarButton("Docs").click();
        docsSection = page(DocsSection.class);
        smokeCheckOfDocsSection(landingBackbase, docsSection);
        logout();
        landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(trainingUserName, trainingUserPassword, false);
        landingBackbase.getNavBarButton("Docs").click();
        docsSection = page(DocsSection.class);
        smokeCheckOfDocsSection(landingBackbase, docsSection);
        docsSection.getNextPageBtn().shouldBe(visible);
        docsSection.getPreviousPageBtn().shouldBe(visible);
        docsSection.getDownloadAllAsPdfBtn().shouldBe(visible);
        docsSection.getVersionInfoExpandBtn().shouldBe(visible);
        docsSection.getVersionInfoExpandBtn().click();
        docsSection.getExpandedVersionInfo().shouldBe(visible);
        docsSection.getPrintPageBtn().shouldBe(visible);
        docsSection.getScrollToTopBtn().shouldBe(visible);
        docsSection.getContentLeftPane().shouldBe(present);
        docsSection.getTrees().shouldHaveSize(2);
        docsSection.getVersionsTree().find(By.tagName("ul")).shouldNotBe(visible);
        docsSection.getExpandTreePlusMarks().get(0).click();
        docsSection.getVersionsTree().find(By.tagName("ul")).shouldBe(visible);
        docsSection.getExpandTreePlusMarks().get(0).click();
        docsSection.getVersionsTree().find(By.tagName("ul")).shouldNotBe(visible);
        docsSection.getActiveGreenSection().shouldBe(visible);
        assertTrue(docsSection.getCollapseTreeMinusMarks().size() > 0);
        docsSection.getSubnavigationSection().shouldBe(visible);
        docsSection.getActiveGreenSection().find(By.tagName("i")).click();
        docsSection.getSubnavigationSection().shouldNotBe(visible);
    }

    @Title("DOCS : Navigation for Docs")
    @Test
    public void testNavigationForDocs() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(companyUserName, companyUserPassword, false);
        landingBackbase.getNavBarButton("Docs").click();
        DocsSection docsSection = page(DocsSection.class);
        docsSection.getNeededDocumentationButton("CXP").click();
        assertTrue("Page URL doesn't contain '/docs' !", url().contains("/docs"));
        docsSection.getContentLeftPane().shouldBe(present);
        docsSection.getNumeredSubsection(3).find(By.tagName("ul")).shouldNotBe(visible);
        docsSection.getNumeredSubsection(3).find(By.tagName("h5")).find(By.tagName("i")).click();
        docsSection.getNumeredSubsection(3).find(By.tagName("ul")).shouldBe(visible);
        docsSection.getNumeredSubsection(3).find(By.tagName("ul")).find(By.tagName("li")).find(By.tagName("a")).click();
        docsSection.getNumeredSubsection(2).find(By.tagName("ul")).shouldNotBe(visible);
        docsSection.getNumeredSubsection(1).find(By.tagName("ul")).shouldNotBe(visible);
        docsSection.getNumeredSubsection(3).find(By.tagName("ul")).shouldBe(visible);
        docsSection.getNumeredSubsection(3).find(By.cssSelector("h5.active")).shouldBe(visible);
        docsSection.getNumeredSubsection(3).find(By.tagName("ul")).find(By.cssSelector("li.active")).shouldBe(visible);
    }

    @Title("DOCS : Archive")
    @Test
    public void testDocsArchive() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(companyUserName, companyUserPassword, false);
        DocsSection docsSection = page(DocsSection.class);
        landingBackbase.getNavBarButton("Docs").hover();
        landingBackbase.getNavBarButton("Docs", "Product Documentation").hover();
        landingBackbase.getNavBarButton("Docs", "Product Documentation", "Documentation Archive").click();
        switchTo().window("Backbase Documentation Archive");
        assertTrue("Title of page should be 'Backbase Documentation Archive', but it's: " + title(), title().contains("Backbase Documentation Archive"));
    }

    @Title("DOCS : Extensions page UI")
    @Test
    public void testDocExtensionUI() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(companyUserName, companyUserPassword, false);
        landingBackbase.getNavBarButton("Docs").hover();
        landingBackbase.getNavBarButton("Docs", "Product Extensions").hover();
        landingBackbase.getNavBarButton("Docs", "Product Extensions", "Extensions").click();
        ExtensionsSection extensionsSection = page(ExtensionsSection.class);
        assertTrue("Title of page should be 'Extensions - My Backbase', but it's: " + title(), title().contains("Extensions - My Backbase"));
        assertTrue("Page URL doesn't contain '/extensions' !", url().contains("/extensions"));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Docs"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Product Extensions"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Extensions"))));
        extensionsSection.getCarousel().shouldBe(visible);
        extensionsSection.getBanner(1).shouldBe(present);
        extensionsSection.getBanner(2).shouldBe(present);
        extensionsSection.getBanner(3).shouldBe(present);
        extensionsSection.getNavTilesWidget().shouldBe(visible);
        extensionsSection.getNavTiles().stream().forEach(w -> w.shouldBe(visible));
        int numOfTiles = extensionsSection.getNavTiles().size();
        int numOfTileImages = extensionsSection.getNavTileImages().size();
        int numOfTileHeaders = extensionsSection.getNavTileHeaders().size();
        int numOfTileSubtitles = extensionsSection.getNavTileSubtitles().size();
        int numOfTileDescr = extensionsSection.getNavTileDescr().size();
        assertTrue(numOfTiles == 10);
        assertTrue((numOfTileDescr == numOfTiles) && (numOfTileHeaders == numOfTiles) && (numOfTileImages == numOfTiles) && (numOfTileSubtitles == numOfTiles));
        extensionsSection.getNavTileContents().get(new Random().nextInt(extensionsSection.getNavTileContents().size() - 1)).click();
        Wait().until(new PredicateURLContains("product-extensions/extensions/"));
        assertTrue("Title of page should be 'product-extensions/extensions/', but it's: " + url(), url().contains("product-extensions/extensions/"));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Home"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Docs"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Product Extensions"))));
        assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Extensions"))));
        extensionsSection.getExtensionDetailsWidget().shouldBe(visible);
        String articleTitle = extensionsSection.getExtensionDetailsWidget().find(By.cssSelector("div.details-holder")).find(By.cssSelector("h1")).getText();
        Wait().until(new PredicateTitleContains(articleTitle));
        assertTrue("Title of page should be 'My Backbase', but it's: " + title(), title().contains("My Backbase"));
        extensionsSection.getHowToSection().shouldBe(visible);
        landingBackbase.getCurrentSectionMarks().find(matchesText("Extensions")).click();
        Wait().until(new PredicateTitleContains("Extensions - My Backbase"));
        assertTrue("Title of page should be 'Extensions - My Backbase', but it's: " + title(), title().contains("Extensions - My Backbase"));
    }

    @Title("DOCS : Forms Documentation UI")
    @Test
    public void testFormsDocumentationUI() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(companyUserName, companyUserPassword, false);
        landingBackbase.getNavBarButton("Docs").hover();
        landingBackbase.getNavBarButton("Docs", "Product Documentation").hover();
        landingBackbase.getNavBarButton("Docs", "Product Documentation", "Forms Documentation").click();
        Wait().until(new PredicateURLContains("docs/product-documentation/documentation"));
        DocsSection docsSection = page(DocsSection.class);
        docsSection.getContentBlock().shouldBe(visible);
        docsSection.getVersionsTree().find(By.cssSelector("h5:nth-child(1)")).shouldHave(text("Forms"));
        docsSection.getPreviousPageBtn().shouldBe(visible);
        docsSection.getNextPageBtn().shouldBe(visible);
        docsSection.getDownloadAllAsPdfBtn().shouldBe(visible);
        docsSection.getVersionInfoExpandBtn().shouldBe(visible);
        docsSection.getVersionInfoExpandBtn().click();
        docsSection.getExpandedVersionInfo().shouldBe(visible);
        docsSection.getPrintPageBtn().shouldBe(visible);
        docsSection.getScrollToTopBtn().shouldBe(visible);
        docsSection.getContentLeftPane().shouldBe(present);
        assertTrue(docsSection.getExpandTreePlusMarks().size() > 0);
        docsSection.getVersionsTree().find(By.cssSelector("ul:nth-child(2)")).shouldNotBe(visible);
        docsSection.getExpandTreePlusMarks().get(0).click();
        docsSection.getVersionsTree().find(By.cssSelector("ul:nth-child(2)")).shouldBe(visible);
        docsSection.getExpandTreePlusMarks().get(0).click();
        docsSection.getVersionsTree().find(By.cssSelector("ul:nth-child(2)")).shouldNotBe(visible);
        docsSection.getActiveGreenSection().shouldBe(visible);
        assertTrue(docsSection.getCollapseTreeMinusMarks().size() > 0);
        docsSection.getSubnavigationSection().shouldBe(visible);
        docsSection.getActiveGreenSection().find(By.tagName("i")).click();
        docsSection.getSubnavigationSection().shouldNotBe(visible);
        docsSection.getActiveGreenSection().shouldBe(visible);
        docsSection.getActiveGreenSection().find(By.tagName("i")).shouldBe(visible);
        docsSection.getSubnavigationSection().shouldNotBe(visible);
        docsSection.getActiveGreenSection().find(By.tagName("i")).click();
        docsSection.getSubnavigationSection().shouldBe(visible);
        System.out.println(); //TODO remove such thing: assertTrue(landingBackbase.getCurrentSectionMarks().stream().anyMatch(m -> m.has(text("Docs"))));
    }
}
