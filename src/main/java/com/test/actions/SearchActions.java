package com.test.actions;

import com.test.base.BaseActions;
import com.test.entity.Book;
import com.test.pages.Pages;
import com.test.pages.SearchResultPage;

import java.util.ArrayList;
import java.util.List;

public class SearchActions extends BaseActions {

    public List<Book> getBooks() {
        SearchResultPage resultPage = Pages.searchResultPage();
        List<Book> books = new ArrayList<>();
        if (resultPage.getBooksNumber() != 0) {
            resultPage.getBooksName();
            for (int i = 0; i < resultPage.getBooksNumber(); i++) {
                Book book =
                        new Book(
                                resultPage.getBooksName().get(i),
                                resultPage.getBooksAuthor().get(i),
                                resultPage.getBooksPrice().get(i),
                                resultPage.getBooksRatio().get(i),
                                resultPage.getBooksBestSeller().get(i)
                        );

                System.err.println(book.toString());

                books.add(book);
            }
        }
        return books;
    }
}

