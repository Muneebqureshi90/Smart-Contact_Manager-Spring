package com.smartcontactmanger.smartcontactmangerproject.entity;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;
import javax.lang.model.element.Name;

@Entity
@Table(name = "CONTACT")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    private String name;
    private String secondName;
    private String work;
    @Column(unique = true)

    private String phone;
    @Column(unique = true)
    private String email;
    private String image;

    @ManyToOne
    private User user;
    @Column(length = 50000)
    private String description;

    public Contact() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public Contact(int cid, String name, String secondName, String work, String phone, String email, String image, User user, String description) {
        this.cid = cid;
        this.name = name;
        this.secondName = secondName;
        this.work = work;
        this.phone = phone;
        this.email = email;
        this.image = image;
        this.user = user;
        this.description = description;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", work='" + work + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
