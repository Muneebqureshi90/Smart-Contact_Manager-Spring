package com.smartcontactmanger.smartcontactmangerproject.services.implmentation;



import com.smartcontactmanger.smartcontactmangerproject.services.EmailServies;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailServiceImple implements EmailServies {


    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendMail(MultipartFile file, String to, String[] cc, String subject, String body) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setCc(cc);
            if (cc != null) {
                mimeMessageHelper.setCc(cc);
            } else {
                mimeMessageHelper.setCc(new String[]{}); // Empty array
            }
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);



            // For Attach File
            if (file != null && !file.isEmpty()) {
                mimeMessageHelper.addAttachment(
                        file.getOriginalFilename(),
                        new ByteArrayResource(file.getBytes())
                );
            }

            javaMailSender.send(mimeMessage);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
