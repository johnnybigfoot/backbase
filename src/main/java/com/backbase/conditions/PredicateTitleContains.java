package com.backbase.conditions;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Created by BigFoot on 21.03.2016.
 */
public class PredicateTitleContains implements com.google.common.base.Predicate {
    String title;

    public PredicateTitleContains(String partOfTitle) {
        this.title = partOfTitle;
    }

    @Override
    public boolean apply(Object o) {
        return getWebDriver().getTitle().contains(title);
    }
}
