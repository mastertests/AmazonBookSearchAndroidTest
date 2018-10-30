package com.test.pages;

import com.test.base.BasePage;
import com.test.entity.Book;
import com.test.locators.Locator;
import com.test.locators.XPath;
import com.test.util.reporter.Reporter;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class SearchResultPage extends BasePage {
    private final Locator resultItems = new XPath("//ul[@id='resultItems']/li/a");
    private final String bookContainer = "//li[@class='sx-table-item']";

    private final String bookName = "//div[@class='sx-table-detail']/h5/span";
    private final String bookAuthor = "//div[@class='a-row a-spacing-micro a-size-small a-color-secondary']/span[2]";
    private final String bookPrice = "//div[@class='a-row']/span[@class='a-size-small a-color-price a-text-bold']";
    private final String bookRatio = "//span[@class='a-icon-alt']";
    private final String bookBestSeller = "//span[@class='a-badge']";

    private void setSearchText(String searchString) {
        HeaderPage page = Pages.headerPage();

        page.setSearchString(searchString);
        page.clickSearchButton();
    }

    public List<Book> getSearchBookResult(String searchString) {
        setSearchText(searchString);

        Reporter.log("Getting result items for search --> " + searchString + "  ");

        List<WebElement> resultItems = getElements(this.resultItems);

        List<Book> books = new ArrayList<>();

        for (int i = 1; i <= resultItems.size(); i++) {

            String resultItem = bookContainer + "[" + i + "]";

            String name = getElement(new XPath(resultItem + bookName)).getText();

            String author = getElement(new XPath(resultItem + bookAuthor)).getText();

            double price = Double.valueOf(getElement(new XPath(resultItem + bookPrice)).getText().replace("$", ""));

            String ratio = "No ratio";
            if (isElementPresent(new XPath(resultItem + bookRatio))) {
                ratio = executeJSWithReturn(
                        "return arguments[0].textContent;",
                        getElement(new XPath(resultItem + bookRatio)));
                ratio = ratio.replace(" out of 5 stars", "");
            }

            boolean isBestSeller = isElementPresent(new XPath(resultItem + bookBestSeller));

            Book book = new Book(name, author, price, ratio, isBestSeller);

            System.out.println(book.toString());
            books.add(book);
        }

        return books;
    }
}
