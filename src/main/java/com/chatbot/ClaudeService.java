import okhttp3.*;
import org.json.*;

public class ClaudeService {

    private static final String API_KEY = System.getenv("GROQ_API_KEY");
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    public String sendMessage(String userMessage) throws Exception {
        OkHttpClient client = new OkHttpClient();

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", userMessage);

        JSONArray messages = new JSONArray();
        messages.put(message);

        JSONObject body = new JSONObject();
        body.put("model", "llama-3.3-70b-versatile");
        body.put("messages", messages);
        body.put("max_tokens", 1024);

        RequestBody requestBody = RequestBody.create(
                body.toString(),
                MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("DEBUG: " + responseBody);
            JSONObject json = new JSONObject(responseBody);
            return json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        }
    }
}
