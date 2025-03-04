package com.exampe.Greeting.controller;
import com.exampe.Greeting.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String message) {
        emailService.sendSimpleEmail(to, subject, message);
        return "Email Sent Successfully!";
    }

    @PostMapping("/sendWithAttachment")
    public String sendEmailWithAttachment(@RequestParam String to, @RequestParam String subject,
                                          @RequestParam String message, @RequestParam String filePath) {
        emailService.sendEmailWithAttachment(to, subject, message, filePath);
        return "Email Sent with Attachment Successfully!";
    }
}