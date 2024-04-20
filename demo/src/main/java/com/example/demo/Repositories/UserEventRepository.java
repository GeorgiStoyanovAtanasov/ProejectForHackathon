package com.example.demo.Repositories;

import com.example.demo.Entities.Event;
import com.example.demo.Entities.User;
import com.example.demo.Entities.UserEvent;
import org.springframework.data.repository.CrudRepository;

public interface UserEventRepository extends CrudRepository<UserEvent, Long> {
    UserEvent findByUser(User user);
}
