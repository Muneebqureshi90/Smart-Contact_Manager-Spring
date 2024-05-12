package com.smartcontactmanger.smartcontactmangerproject.dao;

import com.smartcontactmanger.smartcontactmangerproject.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

//    Finding the contact from user ID
//    Paging
@Query("from Contact as c where c.user.id = :userId")
    public List<Contact> findAllByUser(@Param("userId") int userId);


//    public Page<Contact> findAllByUser(@Param("userId") int userId, Pageable pageable);

}
