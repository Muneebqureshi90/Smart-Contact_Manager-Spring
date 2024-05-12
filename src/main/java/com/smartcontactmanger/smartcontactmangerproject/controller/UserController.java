package com.smartcontactmanger.smartcontactmangerproject.controller;

import com.smartcontactmanger.smartcontactmangerproject.dao.ContactRepository;
import com.smartcontactmanger.smartcontactmangerproject.dao.UserRespositary;
import com.smartcontactmanger.smartcontactmangerproject.entity.Contact;
import com.smartcontactmanger.smartcontactmangerproject.entity.User;
import com.smartcontactmanger.smartcontactmangerproject.helper.Message;
import com.smartcontactmanger.smartcontactmangerproject.services.ImageUploadService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRespositary respositary;
    @Autowired
    private ContactRepository contactRepository;


    @Autowired
    public UserController(UserRespositary theUserRespositary){
        respositary = theUserRespositary;
    }

public UserController(ContactRepository theContactRepository){
        contactRepository= theContactRepository;
}


//    For all methods for getting the information of user
    @ModelAttribute
    public void addCommonData(Model m , Principal principal){
        String userName = principal.getName();
        System.out.println("UserName is " +userName);

        User user= respositary.getUserByUserName(userName);
        System.out.println("User" + user);
//for sending this user to html fole
        m.addAttribute("user",user);
    }



    @GetMapping("/index")
    public String dashboard(Model model, Principal principal){

//        Same also write in above method
//        For Getting user ID or username
        String userName = principal.getName();
        System.out.println("UserName is " +userName);

       User user= respositary.getUserByUserName(userName);
        System.out.println("User" + user);
//for sending this user to html fole
        model.addAttribute("user",user);
        model.addAttribute("title","Home- Contact Manager");
        return "normal/user_dashboard";
    }

    @GetMapping("/add_contact")
    public String dashboard(Model model){

//        For Getting user ID or username

        model.addAttribute("title","Add Contact");
        model.addAttribute("contact",new Contact());

        return "normal/add_contact_form";
    }


//    code without image
    /*
    @PostMapping("/process-contact")
    public String contact_processing(@ModelAttribute @Valid Contact contact,
                                     //@RequestPart("image")
                                     //MultipartFile file,
                                     Principal principal, HttpSession session) {

        try {
            String name = principal.getName();
            User user = this.respositary.getUserByUserName(name);

            // Commented out the image uploading code
            // ...

            // Set the user for the contact
            contact.setUser(user);

            // Add the contact to the user's contacts
            user.getContacts().add(contact);

            // Save the user, which will also save the contact
            this.respositary.save(user);

            System.out.println("The Info is " + contact);
            session.setAttribute("message", new Message("Your Contact is Added", "success"));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions here (e.g., show an error message)
            session.setAttribute("message", new Message(e.getMessage() + "SomeThing Wrong!!", "danger"));

        }

        return "normal/add_contact_form"; // Redirect to the appropriate page
    } */




// This code for upload image but giving me error


    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/process-contact")
    public String contact_processing(@ModelAttribute @Valid Contact contact,
                                     @RequestParam("image") MultipartFile file,
                                     Principal principal, HttpSession session) {

        try {
            String name = principal.getName();
            User user = this.respositary.getUserByUserName(name);

            if (file != null && !file.isEmpty()) {
                // Save the image using the ImageUploadService
                String uniqueFilename = imageUploadService.save(file, "user-case"); // Replace "user-case" with the appropriate value

                // Update the contact's image field with the new filename
                contact.setImage(uniqueFilename);
            }

            // Set the user for the contact
            contact.setUser(user);

            // Add the contact to the user's contacts
            user.getContacts().add(contact);

            // Save the user, which will also save the contact
            this.respositary.save(user);

            System.out.println("The Info is " + contact);
            session.setAttribute("message", new Message("Your Contact is Added", "success"));
        } catch (Exception e) {
            e.printStackTrace();

            // Handle exceptions here (e.g., show an error message)
            session.setAttribute("message", new Message(e.getMessage() + " Something went wrong!!", "danger"));
        }

        return "normal/add_contact_form"; // Redirect to the appropriate page
    }



//    Applying Paging

//    @GetMapping("/show-contacts/{page}")
    @GetMapping("/show-contacts")
    public String showContacts( Integer page, Model model, Principal principal) {
//    public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal) {

        try {
            model.addAttribute("title", "Show Contacts");

            String name = principal.getName();
            User user = this.respositary.getUserByUserName(name);

            // Assuming you have a service layer to handle pagination and contact retrieval
//            Pageable pageable = PageRequest.of(page, 5); // Import PageRequest here
// Page<Contact> contacts = this.contactRepository.findAllByUser(user.getId(), pageable);

            List<Contact> contacts = this.contactRepository.findAllByUser(user.getId());

//            model.addAttribute("currentPage", page);
            model.addAttribute("contact", contacts);
//            model.addAttribute("totalPages", contacts.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while fetching contacts.");
        }

        return "normal/show_contacts";
    }

    @GetMapping("/contact/{cid}")
    public String showContactDetails(@PathVariable("cid") Integer cId, Model model,Principal principal) {
        try {
            // Attempt to find the contact by ID
            Optional<Contact> contact = contactRepository.findById(cId);

            if (contact.isPresent()) {

                String userName = principal.getName();
                this.respositary.getUserByUserName(userName);

                Contact contact1 = contact.get();
                model.addAttribute("model", contact1);
                return "normal/contact_details"; // Return the template name
            } else {
                // Handle the case where the contact was not found
                return "error/contact_not_found"; // You can create an error template for this case
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the process
            e.printStackTrace();
            return "error/internal_error"; // You can create an error template for internal errors
        }
    }

    @GetMapping("/delete/{cid}")
    public String deleteContact(@PathVariable("cid") Integer cId, Model model, HttpSession session) {
        try {
            Optional<Contact> contact = this.contactRepository.findById(cId);

            if (contact.isPresent()) {
                Contact contact1 = contact.get();
                this.contactRepository.delete(contact1);
                session.setAttribute("message", new Message("Contact Is Deleted", "success"));
            } else {
                session.setAttribute("message", new Message("Contact Not Found", "danger"));
            }

            return "redirect:/user/show-contacts";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("Error deleting contact", "danger"));
            return "redirect:/user/show-contacts";
        }
    }
    @GetMapping("/profile")
    public String yourProfile(Model model){

        model.addAttribute("title","Profile Page");

        return "normal/profile";
    }

}
