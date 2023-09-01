package com.bubblebubble.hr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String email, String empNo, String temporaryPassword)  {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("zixuuxiz@naver.com", "BUR-HR");

            helper.setTo(email);
            helper.setSubject("등록하신 직원의 계정 정보 입니다.");

            String emailContent = "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "</div>"
                    + "</div>"
                    + "<h1>등록하신 직원의 계정 정보 입니다.</h1>"
                    + "<p style=\"font-size: 18px;\">사원번호: " + empNo + "</p>"
                    + "<p style=\"font-size: 18px;\">임시 비밀번호: " + temporaryPassword + "</p>"
                    + "<div class=\"button-container\">"
                    + "<button class=\"custom-button\"><a href=\"http://localhost:3000/login\" style=\"color: white; text-decoration: none;\">로그인하러가기</a></button>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            helper.setText(emailContent, true);
            this.javaMailSender.send(message);


            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending email: " + e.getMessage());
        }
    }


}
