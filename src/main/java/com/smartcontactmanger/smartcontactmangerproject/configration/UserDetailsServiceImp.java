package com.smartcontactmanger.smartcontactmangerproject.configration;

import com.smartcontactmanger.smartcontactmangerproject.dao.UserRespositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRespositary userRespositary;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.smartcontactmanger.smartcontactmangerproject.entity.User user = userRespositary.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could Not Found User");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;
    }
}
