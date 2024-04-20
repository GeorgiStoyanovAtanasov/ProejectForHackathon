package com.example.demo.Repositories;

import com.example.demo.Entities.User;
import com.example.demo.Utils.UserSkills;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByUserSkills(UserSkills skill);
}
