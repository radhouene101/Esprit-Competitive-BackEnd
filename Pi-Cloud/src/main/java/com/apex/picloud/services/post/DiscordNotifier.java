package com.apex.picloud.services.post;

import com.apex.picloud.models.Post;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
@Component
@CrossOrigin(origins = "http://localhost:4200")
public class DiscordNotifier {
    private static final Logger logger = LoggerFactory.getLogger(DiscordNotifier.class);

    private final String WEBHOOK_URL = "https://discord.com/api/webhooks/1235001772548948078/-5o9qoiWl7alGLxtDcBSjbK90XDSUEC7PlpzFRJTrVvvwec4K-5I-9Ai6ZJbtGfXtKVF";

    public void sharePostToDiscord(Post post) throws JSONException {
        // Constructing the payload JSON object
        JSONObject payloadJson = new JSONObject();
        payloadJson.put("content", "**" + post.getCreatedBy().getName()+ "**\n" + post.getContent());

        // Setting up request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Creating the request entity with the payload and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(payloadJson.toString(), headers);

        // Sending the POST request to the Discord webhook URL
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(WEBHOOK_URL, requestEntity, String.class);
    }
}
