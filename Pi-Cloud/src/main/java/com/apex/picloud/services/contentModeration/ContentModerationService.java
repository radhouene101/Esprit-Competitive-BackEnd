package com.apex.picloud.services.contentModeration;

import com.apex.picloud.models.User;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.email.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ContentModerationService implements IContentModerationService{
    private Set<String> forbiddenWords ;
    private final UserRepository userRepository;
    private final EmailService emailService ;
    private static final Logger logger = LoggerFactory.getLogger(ContentModerationService.class);
    public enum ActionType {
        ADD_POST,
        ADD_COMMENT,
        UPDATE_POST,
        UPDATE_COMMENT,
        DELETE_POST,
        DELETE_COMMENT
    }


    public ContentModerationService(UserRepository userRepository,EmailService emailService) {
        this.userRepository = userRepository;
        this.forbiddenWords = new HashSet<>();
        this.emailService = emailService ;
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
    @Transactional

    public boolean containsForbiddenWords(String text ) {
        String[] words = text.toLowerCase().split("\\s+");
        for (String word : words) {
            if (forbiddenWords.contains(word)) {
                logger.info("Forbidden word found: {}", word);
                return true;
            }
        }
        return false;
    }

    public String replaceForbiddenWords(String text) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            if (forbiddenWords.contains(words[i].toLowerCase())) {
                int wordLength = words[i].length();
                words[i] = "*".repeat(wordLength);
            }
        }
        return String.join(" ", words);    }
    public void checkUserBadWordCount(User user) throws MessagingException {
        logger.info("User data before incrementation: {}", user);

        User currentUser = userRepository.findById(user.getId()).orElse(null);
        if (currentUser == null) {
            logger.error("User not found in the database!");
            return;
        }

        int wordCount = currentUser.getBadWordCount() + 1;
        currentUser.setBadWordCount(wordCount);

        logger.info("Incremented bad word count: {}", wordCount);

      /*  if (wordCount == 3) {
            String userEmail = currentUser.getEmail();

            String subject = "Warning: Excessive use of forbidden words";
            String body = "You have used forbidden words excessively. Please review your language usage.";
            try {
                emailService.sendEmail(userEmail, subject, body);
                logger.info("Email notification sent successfully to user: {}", userEmail);
            } catch (MessagingException e) {
                logger.error("Failed to send email notification: {}", e.getMessage());
            }
        }*/
        if (wordCount == 3) {
            // Replace userEmail with your Mailtrap inbox email address
            String mailtrapInbox = "from@example.com";

            String subject = "Warning: Excessive use of forbidden words";
            String body = "You have used forbidden words excessively. Please review your language usage.";
            try {
                // Send email to Mailtrap inbox
                emailService.sendEmail(mailtrapInbox, subject, body);
                logger.info("Email notification sent successfully to Mailtrap inbox: {}", mailtrapInbox);
            } catch (MessagingException e) {
                logger.error("Failed to send email notification: {}", e.getMessage());
            }
        }

        else if (wordCount >= 5) {
            banUser(currentUser);
        }

        User updatedUser = userRepository.save(currentUser);

        logger.info("User data after saving: {}", updatedUser);
    }



    private void banUser(User user) throws MessagingException {
        String mailtrapInbox = "from@example.com";
        String subject = "You have been banned !";
        String body = "Your account has been banned for a week for the use of bad words ! ";

        if (user != null) {
            user.setBanned(true);
            user.setBanStartTime(LocalDateTime.now());
            userRepository.save(user);
        }
        emailService.sendEmail(mailtrapInbox, subject, body);
        logger.info("Banning email notification sent successfully to Mailtrap inbox: {}", mailtrapInbox);
    }
    public void performAction(User currentUser , ActionType actionType) {
        if (currentUser == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if ((currentUser.getBanned())==true) {
            throw new IllegalStateException("User is banned and cannot perform this action");
        }

        switch (actionType) {
            case ADD_POST:
            case ADD_COMMENT:
            case UPDATE_POST:
            case UPDATE_COMMENT:
            case DELETE_POST:
            case DELETE_COMMENT:
                throw new IllegalStateException("User is banned and cannot perform this action");

            default:
                throw new IllegalArgumentException("Invalid action type");
        }
    }

}


