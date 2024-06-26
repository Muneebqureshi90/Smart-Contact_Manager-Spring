package com.smartcontactmanger.smartcontactmangerproject.controller;


import com.smartcontactmanger.smartcontactmangerproject.services.EmailServies;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mail")
public class EmailController {
    private EmailServies emailServices;

    public EmailController( EmailServies emailServices) {
        this.emailServices = emailServices;
    }

    @PostMapping("/send")
    public String sendMail(@RequestParam(value = "file", required = false) MultipartFile file ,
                           String to,String[] cc,String subject,String body
    ){

        return emailServices.sendMail(file,to,cc,subject,body);

    }
}
