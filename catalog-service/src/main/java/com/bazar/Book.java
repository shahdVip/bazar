package com.bazar;

public class Book {
    public int id;
    public String title;
    public String topic;
    public int quantity;
    public double price;

    public Book() {} // Gson needs this

    public Book(int id, String title, String topic, int quantity, double price) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.quantity = quantity;
        this.price = price;
    }
}
