package com.chatbot;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        int port = System.getenv("PORT") != null
                ? Integer.parseInt(System.getenv("PORT"))
                : 8080;

        port(port);
        staticFiles.location("/static");

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
        });

        options("/*", (req, res) -> "OK");
        post("/chat", new ChatHandler());

        System.out.println("✅ Chatbot running on port " + port);
    }
}
