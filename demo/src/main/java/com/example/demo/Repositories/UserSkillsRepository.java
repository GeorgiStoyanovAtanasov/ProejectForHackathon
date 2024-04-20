package com.example.demo.Repositories;

import com.example.demo.Entities.User;
import com.example.demo.Utils.UserSkills;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface UserSkillsRepository extends CrudRepository<User,Long> {
    List<User> findByUserSkillsIn(List<UserSkills> userSkills);
}
