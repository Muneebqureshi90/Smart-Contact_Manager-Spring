package com.smartcontactmanger.smartcontactmangerproject.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class sessionHelper {

    public void removeMessageFromSession() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attr.getRequest();
            HttpSession session = request.getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
