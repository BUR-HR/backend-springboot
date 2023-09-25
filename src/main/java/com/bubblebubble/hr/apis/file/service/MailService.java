package com.bubblebubble.hr.apis.file.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bubblebubble.hr.apis.payment.entity.Payroll;
import com.bubblebubble.hr.apis.payment.repository.PayrollRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final PayrollRepository payrollRepository;

    public void sendEmail(String email, int empNo, String temporaryPassword) {
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
                    ".custom-button { background-color: #304352; border: none; padding: 10px 20px; color: white; text-decoration: none; }"
                    +
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
                    "<a class=\"custom-button\" href=\"http://localhost:3000/login\" style=\"background-color: #304352; color: white; text-decoration: none; display: inline-block; padding: 10px 20px; border-radius: 5px;\">로그인하러가기</a>"
                    +
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

    public void sendPaymentEmail(int no) throws MessagingException, IOException {

        List<Payroll> payrolls = payrollRepository.findByPayrollNo(no);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        for (var payroll : payrolls) {

            String email = payroll.getEmployeeAndJob().getEmployeeEmail();
            helper.setFrom("zixuuxiz@naver.com", "BUR-HR");
            helper.setTo(email);
            helper.setSubject("등록하신 직원의 계정 정보 입니다.");
    
            Path temp = Files.createTempFile("temp_", ".html");
            String content = "<!DOCTYPE html>\r\n" + //
                    "<html lang=\"ko\">\r\n" + //
                    "\r\n" + //
                    "<head>\r\n" + //
                    "    <meta charset=\"UTF-8\">\r\n" + //
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + //
                    "    <title>Document</title>\r\n" + //
                    "    <style>\r\n" + //
                    "        form {\r\n" + //
                    "            display: flex;\r\n" + //
                    "            align-items: center;\r\n" + //
                    "        }\r\n" + //
                    "\r\n" + //
                    "        .rrn {\r\n" + //
                    "            width: 120px;\r\n" + //
                    "            height: 20px;\r\n" + //
                    "            margin-right: 10px;\r\n" + //
                    "            padding: 0px;\r\n" + //
                    "        }\r\n" + //
                    "\r\n" + //
                    "        button {\r\n" + //
                    "            display: flex;\r\n" + //
                    "            width: 60px;\r\n" + //
                    "            height: 24px;\r\n" + //
                    "            padding: 2px 10px;\r\n" + //
                    "            justify-content: center;\r\n" + //
                    "            align-items: center;\r\n" + //
                    "            gap: 1px;\r\n" + //
                    "            border-radius: 5px;\r\n" + //
                    "            border: 0px;\r\n" + //
                    "            background: #000;\r\n" + //
                    "            color: white;\r\n" + //
                    "        }\r\n" + //
                    "\r\n" + //
                    "        button:hover {\r\n" + //
                    "            background-color: #3b3b3b;\r\n" + //
                    "        }\r\n" + //
                    "\r\n" + //
                    "        button:active {\r\n" + //
                    "            background-color: #5b5b5b;\r\n" + //
                    "        }\r\n" + //
                    "    </style>\r\n" + //
                    "    <script>\r\n" + //
                    "        const onSubmitHandler = (e) => {\r\n" + //
                    "            e.preventDefault();\r\n" + //
                    "            const formData = new FormData(e.target);\r\n" + //
                    "\r\n" + //
                    "            fetch('http://localhost:8080/api/mail/payment', {\r\n" + //
                    "                method: 'post',\r\n" + //
                    "                body: formData\r\n" + //
                    "            })\r\n" + //
                    "                .then(res => res.text())\r\n" + //
                    "                .then(data => {\r\n" + //
                    "                    document.getElementById('root').innerHTML(data);\r\n" + //
                    "                })\r\n" + //
                    "                .catch(err => {\r\n" + //
                    "                    console.log(err);\r\n" + //
                    "                })\r\n" + //
                    "        }\r\n" + //
                    "    </script>\r\n" + //
                    "</head>\r\n" + //
                    "\r\n" + //
                    "<body id='root'>\r\n" + //
                    "    <span>급여명세서</span>\r\n" + //
                    "    <form onsubmit=\"onSubmitHandler(event)\">\r\n" + //
                    "        <input name='secret' type=\"hidden\" value='' />\r\n" + //
                    "        주민등록번호 입력 : <input name='rrn' class='rrn' type='text' />\r\n" + //
                    "        <button>전송</button>\r\n" + //
                    "    </form>\r\n" + //
                    "\r\n" + //
                    "</body>\r\n" + //
                    "\r\n" + //
                    "</html>";
            temp = Files.write(temp, content.getBytes("UTF-8"));
            helper.addAttachment("급여명세서.html", temp.toFile());
    
            javaMailSender.send(message);
            Files.delete(temp);
        }
    }
}
