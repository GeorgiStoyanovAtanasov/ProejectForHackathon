package com.example.demo.Repositories;

import com.example.demo.Entities.Event;
import com.example.demo.Entities.User;
import com.example.demo.Entities.UserEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserEventRepository extends CrudRepository<UserEvent, Long> {
    UserEvent findByUser(User user);
    List<UserEvent> findAllByEvent(Event event);
}
