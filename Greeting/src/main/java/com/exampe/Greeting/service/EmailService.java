package com.exampe.Greeting.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
            System.out.println("✅ Email Sent Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmailWithAttachment(String to, String subject, String message, String filePath) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            helper.addAttachment("Attachment", new File(filePath));
            mailSender.send(mimeMessage);
            System.out.println("✅ Email Sent with Attachment Successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}