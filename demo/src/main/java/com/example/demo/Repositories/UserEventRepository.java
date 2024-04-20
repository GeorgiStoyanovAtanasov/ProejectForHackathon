package com.example.demo.Repositories;

import com.example.demo.Entities.Event;
import com.example.demo.Entities.User;
import com.example.demo.Entities.UserEvent;
import com.example.demo.Utils.UserSkills;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserEventRepository extends CrudRepository<UserEvent, Long> {
    List<UserEvent> findByUser_UserSkillsIn(Set<UserSkills> userSkills);
}
