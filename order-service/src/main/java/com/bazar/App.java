package com.bazar;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;

public class App {
    public static void main(String[] args) {
        port(4569); // Order server

        // CORS support
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*"); // Allows all origins
            res.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS"); // Allowed methods
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Allowed headers
        });

        // Handle preflight requests (OPTIONS)
        options("/*", (req, res) -> {
            res.status(200);
            return "OK";
        });

        // Define the route for order purchase
        post("/purchase/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            String catalogUrl = "http://localhost:4568/info/" + id;

            // Call catalog to get book info
            URL url = new URL(catalogUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                jsonResponse.append(line);
            }
            in.close();

            Gson gson = new Gson();
            JsonObject book = gson.fromJson(jsonResponse.toString(), JsonObject.class);
            int quantity = book.get("quantity").getAsInt();

            if (quantity <= 0) {
                res.status(400);
                return "Purchase failed: Book is out of stock.";
            }

            // Decrease quantity by 1
            int newQty = quantity - 1;
            book.addProperty("quantity", newQty);

            // Simulate update
            System.out.println("Purchased book: " + book.get("title").getAsString());

            return "Successfully purchased: " + book.get("title").getAsString();
        });
    }
}
