package com.apex.picloud.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component

public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendSetPasswordEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("set password");
        mimeMessageHelper.setText("""
        <div>
          <a href="http://localhost:4200/forgotpassword" target="_blank">click link to set password</a>
        </div>
        """.formatted(email), true);

        javaMailSender.send(mimeMessage);
    }

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText("""
                <div style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; border-radius: 5px; text-align: center;">
                         <img src="lien_vers_votre_logo" alt="Logo de votre entreprise" style="width: 100px; height: auto; margin-bottom: 20px;">
                         <p style="font-size: 16px; color: #333; margin-bottom: 20px;">Bienvenue sur notre plateforme ! Pour activer votre compte, cliquez sur le bouton ci-dessous :</p>
                         <a href="http://localhost:4200/activateAccount/%s/%s" target="_blank" style="display: inline-block; background-color: #007bff; color: #fff; text-decoration: none; padding: 10px 20px; border-radius: 5px;">Activer le compte</a>
                       </div>
                       
        """.formatted(email, otp), true);

        javaMailSender.send(mimeMessage);
    }
}
