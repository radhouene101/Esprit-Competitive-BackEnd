package com.apex.picloud.services.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final Properties props;

    public EmailService() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        props.put("mail.transport.protocol", "smtp");
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        logger.info("Sending email to: {}", to);
        logger.info("SMTP Host: {}, Port: {}", props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.port"));
        logger.info("Email Subject: {}", subject);
        logger.info("Email Body: {}", body);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("e88029d843d5a4", "5ddfe1df461f25");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("noreply@example.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        try {
            Transport.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email to: {}", to, e);
            throw e; // Re-throw the exception to propagate it up the call stack
        }
    }
}
