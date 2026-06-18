package com.chatbot;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        staticFiles.location("/static");

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
        });

        options("/*", (req, res) -> "OK");
        post("/chat", new ChatHandler());

        System.out.println("Chatbot running at http://localhost:8080");
    }
}