package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    //get user based on username
    User findByUserName(String username);

    @Transactional
    void deleteByUserName(String username);

    List<User> findAllByIsDeletedIsFalse();


    List<User> findByRoleDescriptionIgnoreCase(String description);



}
