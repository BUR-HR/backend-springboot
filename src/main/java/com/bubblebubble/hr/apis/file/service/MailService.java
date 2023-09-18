package com.bubblebubble.hr.apis.file.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String email, String empNo, String temporaryPassword) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("zixuuxiz@naver.com", "BUR-HR");
            helper.setTo(email);
            helper.setSubject("등록하신 직원의 계정 정보 입니다.");

            String emailContent = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; margin: 0; padding: 0; }" +
                    ".container { background-color: #f4f4f4; padding: 20px; }" +
                    ".header { background-color: #304352; color: white; padding: 10px; text-align: center; }" +
                    ".content { padding: 20px; }" +
                    ".button-container { text-align: center; }" +
                    ".custom-button { background-color: #304352; border: none; padding: 10px 20px; color: white; text-decoration: none; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "<div class=\"header\">" +
                    "<h1>등록하신 직원의 계정 정보 입니다.</h1>" +
                    "</div>" +
                    "<div class=\"content\">" +
                    "<p style=\"font-size: 18px;\">사원번호: " + empNo + "</p>" +
                    "<p style=\"font-size: 18px;\">임시 비밀번호: " + temporaryPassword + "</p>" +
                    "<div class=\"button-container\">" +
                    "<a class=\"custom-button\" href=\"http://localhost:3000/login\" style=\"background-color: #304352; color: white; text-decoration: none; display: inline-block; padding: 10px 20px; border-radius: 5px;\">로그인하러가기</a>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailContent, true);
            this.javaMailSender.send(message);

            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending email: " + e.getMessage());
        }
    }


}
