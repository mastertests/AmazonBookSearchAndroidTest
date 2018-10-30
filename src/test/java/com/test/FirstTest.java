package com.test;

import com.test.actions.Actions;
import com.test.base.BaseTest;
import com.test.entity.Book;
import com.test.pages.Pages;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class FirstTest extends BaseTest {

    @Test
    public void openAmazonPage() {
        Actions.mainActions().openMainPage();
    }

    @Test
    @Parameters({"searchText"})
    public void openResultPage(String searchText) {
        List<Book> books = Pages.searchResultPage().getSearchBookResult(searchText);
        boolean isExist = false;

        for (Book book : books)
            if (book.getName().equals("Head First Java, 2nd Edition"))
                isExist = true;

        if (isExist)
            System.out.println("Head First Java, 2nd Edition is on the list");
        else
            System.out.println("Head First Java, 2nd Edition is NOT on the list");
    }
}
