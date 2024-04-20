package com.apex.picloud.services.contentModeration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class ContentModerationService implements IContentModerationService{
    private Set<String> forbiddenWords ;
    private static final Logger logger = LoggerFactory.getLogger(ContentModerationService.class);
    public ContentModerationService() {
        this.forbiddenWords = new HashSet<>();
        loadForbiddenWords();
    }

    private void loadForbiddenWords() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/ContentModeration.json")) {
            JsonNode rootNode = mapper.readTree(inputStream);
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    String word = node.asText().trim();
                    forbiddenWords.add(word);
                    logger.info("Loaded forbidden word: {}", word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error loading forbidden words: {}", e.getMessage()); // Add logging statement here
        }
    }

    public boolean containsForbiddenWords(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        for (String word : words) {
            if (forbiddenWords.contains(word)) {
                logger.info("Forbidden word found: {}", word);
                return true;
            }
        }
        return false;
    }
}
