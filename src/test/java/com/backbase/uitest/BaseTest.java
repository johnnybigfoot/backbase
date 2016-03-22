package com.backbase.uitest;

import com.backbase.uitests.pages.LandingBackbase;
import com.backbase.utils.Config;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by BigFoot on 02.03.2016.
 */
public class BaseTest {
    String backbaseUrl = Config.getProperty("baseUrl");
    int driverTimeoutInSeconds = Integer.parseInt(Config.getProperty("timeout"));
    String browserName = Config.getProperty("driver");

    public void clickWithJS(SelenideElement selenideElement) {
        WebDriver driver = com.codeborne.selenide.WebDriverRunner.getWebDriver();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", selenideElement);
    }

    public boolean isBreadcrumbContainsTexts(LandingBackbase landingBackbase, String... text) {
        String[] existingText = landingBackbase.getCurrentSectionMarks().getTexts();
        List<String> exText = new ArrayList<>();
        for (int i = 0; i < existingText.length; i++)
            exText.add(existingText[i].replaceAll("\n", "").replaceAll("»", ""));
        return exText.containsAll(Arrays.asList(text));
    }
}
