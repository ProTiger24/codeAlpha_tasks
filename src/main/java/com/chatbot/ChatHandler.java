package com.chatbot;

import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class ChatHandler implements Route {

    private final ClaudeService claudeService = new ClaudeService();

    @Override
    public Object handle(Request req, Response res) throws Exception {
        res.type("application/json");
        try {
            JSONObject body = new JSONObject(req.body());
            String userMessage = body.getString("message");
            String reply = claudeService.sendMessage(userMessage);
            JSONObject result = new JSONObject();
            result.put("reply", reply);
            return result.toString();
        } catch (Exception e) {
            res.status(500);
            JSONObject error = new JSONObject();
            error.put("reply", "Error: " + e.getMessage());
            return error.toString();
        }
    }
}