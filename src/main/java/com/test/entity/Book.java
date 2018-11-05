package com.test.entity;

import java.util.List;
import java.util.Objects;

public class Book {
    private String name;
    private String author;
    private String ratio;
    private double price;
    private boolean bestSeller;

    public Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Book(String name, String author, double price, String ratio, boolean bestSeller) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.ratio = ratio;
        this.bestSeller = bestSeller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        this.bestSeller = bestSeller;
    }

    public static boolean contains(List<Book> books, Book expectedBook) {
        boolean isExist = false;

        for (Book book : books)
            if (book.equals(expectedBook))
                isExist = true;

        return isExist;
    }

    @Override
    public String toString() {
        return String.format(
                "Book added --> Name: %s; Author: %s; Price: %s; Ratio: %s; BestSeller: %s;",
                name,
                author,
                price,
                ratio,
                bestSeller
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(name, book.name) &&
                Objects.equals(author, book.author);
    }
}
