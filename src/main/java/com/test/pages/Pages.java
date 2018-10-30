package com.test.pages;

public class Pages {

    private static LoginPage loginPage;
    private static HeaderPage headerPage;
    private static SearchResultPage searchResultPage;

    public static LoginPage loginPage() {
        if (loginPage == null)
            loginPage = new LoginPage();

        return loginPage;
    }

    public static HeaderPage headerPage() {
        if (headerPage == null)
            headerPage = new HeaderPage();

        return headerPage;
    }

    public static SearchResultPage searchResultPage() {
        if (searchResultPage == null)
            searchResultPage = new SearchResultPage();

        return searchResultPage;
    }

}
