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
        mimeMessageHelper.setSubject("Reset Password");
        mimeMessageHelper.setText("""
    
        <div style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; border-radius: 5px; text-align: center;">
                                               <img src="https://drive.google.com/uc?id=195chz3E64w7nAnHtM5o_uZOHYLBo34q-" alt="Logo de votre entreprise" style="width: 250px; height: auto; margin-bottom: 20px;">
                         <p style="font-size: 16px; color: #333; margin-bottom: 20px;">click the button below:</p>
                         <a href="http://localhost:4200/forgotpassword" target="_blank" style="display: inline-block; background-color: black; color: #fff; text-decoration: none; padding: 10px 20px; border-radius: 5px;">Reset Password</a>
                       </div>
        """.formatted(email), true);

        javaMailSender.send(mimeMessage);
    }

    public void sendMailOrder(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Order Confirmation");
        mimeMessageHelper.setText("""
        <div>
          <h1>letsgo thanks</h1>
        </div>
        """.formatted(email), true);

        javaMailSender.send(mimeMessage);
    }

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Account Activation");
        mimeMessageHelper.setText("""
               <div style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; border-radius: 5px; text-align: center;">
                                               <img src="https://drive.google.com/uc?id=195chz3E64w7nAnHtM5o_uZOHYLBo34q-" alt="Logo de votre entreprise" style="width: 250px; height: auto; margin-bottom: 20px;">
                                               <p style="font-size: 16px; color: #333; margin-bottom: 20px;">Welcome to our platform! </p>
                                               <a href="http://localhost:4200/activateAccount/%s/%s" target="_blank" style="display: inline-block; background-color: black; color: #fff; text-decoration: none; padding: 10px 20px; border-radius: 5px;">Account Activation</a>
                                             </div>
                                             
                       
        """.formatted(email, otp), true);

        javaMailSender.send(mimeMessage);
    }
}
