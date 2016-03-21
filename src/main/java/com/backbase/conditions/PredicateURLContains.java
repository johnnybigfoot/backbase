package com.backbase.conditions;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Created by BigFoot on 21.03.2016.
 */
public class PredicateURLContains implements com.google.common.base.Predicate {
    String url;

    public PredicateURLContains(String urlPart) {
        this.url = urlPart;
    }

    @Override
    public boolean apply(Object o) {
        return getWebDriver().getCurrentUrl().contains(url);
    }
}
