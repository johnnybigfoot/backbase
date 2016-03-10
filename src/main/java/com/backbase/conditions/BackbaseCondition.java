package com.backbase.conditions;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

/**
 * Created by BigFoot on 10.03.2016.
 */
public class BackbaseCondition {
    public static Condition containsSubElement(String tagName) {
        return new Condition("containsSubElement") {
            @Override
            public boolean apply(WebElement element) {
                return element.findElements(By.tagName(tagName)).size() > 0;
            }

            @Override
            public String toString() {
                return name + " Tagname: " + tagName;
            }
        };
    }

    public static Condition containsSubElementByXpath(String xpath) {
        return new Condition("containsSubElementByXpath") {
            @Override
            public boolean apply(WebElement element) {
                if (element.findElements(By.xpath(xpath)).size() > 0) {
                    System.out.println(element.findElement(By.xpath(xpath)).getText());
                    return true;
                } else return false;
            }

            @Override
            public String toString() {
                return name + " Xpath: " + xpath;
            }
        };
    }

    public static Condition containsSubElements(String... tagNames) {
        return new Condition("containsSubElements") {
            @Override
            public boolean apply(WebElement element) {
                boolean apply = false;
                for (String oneTag : tagNames) {
                    if (element.findElements(By.tagName(oneTag)).size() > 0) apply = true;
                    else return false;
                }
                return apply;
            }

            @Override
            public String toString() {
                return name + " Tagnames: " + Arrays.asList(tagNames);
            }
        };
    }
}
