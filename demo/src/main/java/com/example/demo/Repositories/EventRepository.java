package com.example.demo.Repositories;

import com.example.demo.Entities.Event;
import com.example.demo.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
    Event findByName(String name);
    //Event findAllByUser(User user);
}
