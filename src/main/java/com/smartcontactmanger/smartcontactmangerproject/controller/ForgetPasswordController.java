package com.smartcontactmanger.smartcontactmangerproject.controller;

import com.smartcontactmanger.smartcontactmangerproject.services.EmailServies;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgetPasswordController {


    private final EmailServies emailServices;

    // Inject the EmailServices bean from the first project
    public ForgetPasswordController(EmailServies emailServices) {
        this.emailServices = emailServices;
    }


    @RequestMapping("/forget")
    public String openEmailform(){
        return "forget_email_form";
    }
    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email , HttpSession session) {
        // Your logic to send OTP to the specified email goes here

        System.out.println("Email is " + email);

        // Generating a 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a random 6-digit number
        System.out.println("OTP is " + otp);

        // Send an email using the email service
        try {
            // Customize the email subject and body as needed
            String subject = "Your OTP Code From Smart Contact Manager";
            String message = "<h1>  OTP =" + otp + "</h1>";
            String to = email;

            this.emailServices.sendMail(null, email, null, subject, message);

            return "verify_otp";


        } catch (Exception e) {
            e.printStackTrace();
            // Handle email sending failure here
            return "forget_email_form"; // Return an error view or message
        }
    }



}
