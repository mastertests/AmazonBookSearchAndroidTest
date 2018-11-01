package com.test;

import com.test.actions.Actions;
import com.test.base.BaseTest;
import com.test.entity.Book;
import com.test.pages.Pages;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FirstTest extends BaseTest {

    private Book expectedBook;

    @BeforeTest
    @Parameters({"expectedBookName", "expectedBookAuthor"})
    public void setExpectedBook(String expectedBookName, String expectedBookAuthor) {
        expectedBook = new Book(
                expectedBookName,
                expectedBookAuthor
        );
    }

    @Test
    public void openAmazonPage() {
        Actions.mainActions().openMainPage();
    }

    @Test(dependsOnMethods = {"openAmazonPage"})
    @Parameters({"searchText"})
    public void setSearchText(String searchText) {

        Pages.headerPage().setSearchString(searchText);
        Pages.headerPage().clickSearchButton();
    }

    @Test(dependsOnMethods = {"setSearchText"})
    public void openResultPage() {

        Assert.assertTrue(
                Book.containsIn(Pages.searchResultPage().getSearchBookResult(), expectedBook),
                expectedBook.getName() + " is NOT on the list"
        );
    }
}
