package com.bazar;

import static spark.Spark.*;
import com.google.gson.Gson;

public class App {
    public static void main(String[] args) {
        port(4568); // Catalog server runs on port 4568

        CatalogManager manager = new CatalogManager();
        Gson gson = new Gson();

        get("/search/:topic", (req, res) -> {
            String topic = req.params(":topic");
            res.type("application/json");
            return gson.toJson(manager.getBooksByTopic(topic));
        });

        get("/info/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Book book = manager.getBookById(id);
            if (book == null) {
                res.status(404);
                return "Book not found.";
            }
            res.type("application/json");
            return gson.toJson(book);
        });
    }
}
