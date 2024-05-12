package com.smartcontactmanger.smartcontactmangerproject.dao;

import com.smartcontactmanger.smartcontactmangerproject.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;


@Repository
@Transactional
public interface UserRespositary extends JpaRepository< User,Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByUserName(@Param("email") String email);
}
