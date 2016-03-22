package com.backbase.uitest;

import com.backbase.uitests.pages.LandingBackbase;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Title;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertTrue;

public class AuthTest extends BaseTest {
    @Title("Login : Company user (partner or client)")
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
}
