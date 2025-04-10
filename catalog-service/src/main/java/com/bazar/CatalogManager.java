package com.bazar;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class CatalogManager {
    private final String FILE_PATH = "catalog.json";
    private List<Book> books;

    public CatalogManager() {
        loadCatalog();
    }

    private void loadCatalog() {
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Book>>() {}.getType();
            books = gson.fromJson(new FileReader(FILE_PATH), listType);
        } catch (Exception e) {
            books = new ArrayList<>();
            e.printStackTrace();
        }
    }

    public List<Book> getBooksByTopic(String topic) {
        return books.stream()
                .filter(b -> b.topic.equalsIgnoreCase(topic))
                .toList();
    }

    public Book getBookById(int id) {
        return books.stream().filter(b -> b.id == id).findFirst().orElse(null);
    }
}
