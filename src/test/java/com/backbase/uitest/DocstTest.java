package com.backbase.uitest;

import com.backbase.conditions.PredicateTitleContains;
import com.backbase.conditions.PredicateURLContains;
import com.backbase.uitests.pages.DocsSection;
import com.backbase.uitests.pages.ExtensionsSection;
import com.backbase.uitests.pages.LandingBackbase;
import org.junit.Test;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertTrue;

public class DocstTest extends BaseTest {
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
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Docs", "Product Extensions", "Extensions"));
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
        assertTrue(isBreadcrumbContainsTexts(landingBackbase, "Home", "Docs", "Product Extensions", "Extensions"));
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
    }
}
