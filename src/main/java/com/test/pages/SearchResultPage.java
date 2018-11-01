package com.test.pages;

import com.test.actions.Actions;
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
    private final Locator resultItems = new XPath("//ul[@id='resultItems']/li[@class='sx-table-item']");
    private final String tableItem = "//li[@class='sx-table-item']";

    private final Locator bookName = new XPath(tableItem + "//div[@class='sx-table-detail']/h5/span");
    private final Locator bookAuthor = new XPath(tableItem + "//div[@class='sx-table-detail']/div[1]/span[2]");
    private final Locator bookPrice = new XPath(tableItem + "//div[@class='a-row']/span[contains(@class, 'a-text-bold')]");
    private final Locator bookRatio = new XPath(tableItem + "[%s]//span[@class='a-icon-alt']");
    private final Locator bookBestSeller = new XPath(tableItem + "[%s]//span[@class='a-badge']");

    public List<Book> getSearchBookResult() {
        Reporter.log("Getting result items --> ");

        return Actions.searchActions().getBooks();
    }

    public int getBooksNumber() {
        return getElementsCount(resultItems);
    }

    public List<String> getBooksName() {
        List<WebElement> nameElements = getElements(bookName);
        ArrayList<String> names = new ArrayList<>();
        for (WebElement name : nameElements)
            names.add(name.getText());

        return names;
    }

    public List<String> getBooksAuthor() {
        ArrayList<String> authors = new ArrayList<>();
        for (WebElement author : getElements(bookAuthor))
            authors.add(author.getText());

        return authors;
    }

    public List<Double> getBooksPrice() {
        List<WebElement> priceElements = getElements(bookPrice);
        List<Double> prices = new ArrayList<>();
        for (WebElement price : priceElements) {
            prices.add(Double.valueOf(price.getText().replace("$", "")));
        }

        return prices;
    }

    public List<String> getBooksRatio() {
        ArrayList<String> ratios = new ArrayList<>();
        for (int i = 1; i <= getBooksNumber(); i++) {
            ratios.add(setRatio(bookRatio, i));
        }

        return ratios;
    }

    public List<Boolean> getBooksBestSeller() {
        ArrayList<Boolean> bestSellers = new ArrayList<>();
        for (int i = 1; i <= getBooksNumber(); i++) {
            bestSellers.add(isElementPresent(bookBestSeller, i));
        }

        return bestSellers;
    }

    private String setRatio(Locator locator, Object... args) {
        String ratio = "No ratio";
        if (isElementPresent(locator, args)) {
            ratio = executeJSWithReturn(
                    "return arguments[0].textContent;",
                    getElement(locator, args)
            );
            ratio = ratio.replace(" out of 5 stars", "");
        }
        return ratio;
    }
}
