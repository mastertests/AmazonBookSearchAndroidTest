package com.test;

import com.test.base.BaseTest;
import com.test.entity.Book;
import com.test.pages.Pages;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FirstTest extends BaseTest {

    private Book expectedBook;

    @BeforeClass
    @Parameters({"expectedBookName", "expectedBookAuthor"})
    public void setExpectedBook(String expectedBookName, String expectedBookAuthor) {
        expectedBook = new Book(
                expectedBookName,
                expectedBookAuthor
        );
    }

    @Test
    @Parameters({"searchText"})
    public void searchResultTest(String searchText) {

        Pages.headerPage().setSearchString(searchText);
        Pages.headerPage().clickSearchButton();

        Assert.assertTrue(
                Book.containsIn(Pages.searchResultPage().getSearchBookResult(), expectedBook),
                expectedBook.getName() + " is NOT on the list"
        );
    }

}
