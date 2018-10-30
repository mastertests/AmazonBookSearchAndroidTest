package com.test.entity;

public class Book {
    private String name;
    private String author;
    private String ratio;
    private double price;
    private boolean bestSeller;

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
}
