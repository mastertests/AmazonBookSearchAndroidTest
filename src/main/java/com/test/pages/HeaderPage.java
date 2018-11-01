package com.test.pages;

import com.test.base.BasePage;
import com.test.locators.ID;
import com.test.locators.Locator;
import com.test.locators.XPath;

public class HeaderPage extends BasePage {

    private final Locator searchField = new ID("nav-search-keywords");
    private final Locator searchButton = new XPath("//input[@value='Go']");

    public void setSearchString(String searchString) {
        type(
                "Set search string: \"" + searchString + "\" to search field",
                searchString,
                searchField
        );
    }

    public void clickSearchButton() {
        click("Click at search button", searchButton);
    }
}
