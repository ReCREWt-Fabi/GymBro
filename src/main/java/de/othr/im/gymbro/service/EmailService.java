package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sharePlan(String to, Long planId) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("sharing.gymbro@gmail.com");
        helper.setTo(to);
        helper.setSubject("A User shared a GymBro Workout Plan with you!");
        helper.setText(EmailTemplate.sharing(planId), true);
        emailSender.send(message);
    }
}
