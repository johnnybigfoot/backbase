package com.backbase.uitest;

import com.backbase.uitests.pages.DemoSection;
import com.backbase.uitests.pages.LandingBackbase;
import com.backbase.uitests.pages.RequestDemoForm;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import static com.backbase.conditions.BackbaseCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DemosTest extends BaseTest {
    //TODO Maybe, add new widgets in DEMOs content?
    @Title("DEMOS : Videos Content - Availability depending on user type")
    @Test
    public void testDemosForInternalUsers() {
        LandingBackbase landingBackbase = open(baseUrl, LandingBackbase.class);
        landingBackbase.login(internalUserName, internalUserPassword, false);
        landingBackbase.getNavBarButton("Demos").hover();
        landingBackbase.getNavBarButton("Demos", "Videos").click();
        assertTrue("Title of page should be 'Videos - My Backbase', but it's: " + title(), title().contains("Videos - My Backbase"));
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Demos", "Videos"));
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
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Demos", "Backbase Showcase"));
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
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Demos", "Backbase Demo"));
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
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Demos", "Backbase Showcase"));
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
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Demos", "Backbase Demo"));
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
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Demos", "Backbase Showcase"));
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
}
