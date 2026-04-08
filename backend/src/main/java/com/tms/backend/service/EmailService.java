//package com.tms.backend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendEmail(String toEmail, String subject, String body) {
//        try {
//            SimpleMailMessage  message = new SimpleMailMessage();
//            message.setFrom("noreply@tms.com");
//            message.setTo(toEmail);
//            message.setSubject(subject);
//            message.setText(body);
//
//            mailSender.send(message);
//            System.out.println("Mail Sent Successfully to " + toEmail);
//
//        } catch (Exception e) {
//            System.err.println("Failed to send email: " + e.getMessage());
//        }
//    }
//}
