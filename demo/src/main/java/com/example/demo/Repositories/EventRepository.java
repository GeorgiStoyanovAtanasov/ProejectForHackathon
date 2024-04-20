package com.example.demo.Repositories;

import com.example.demo.Entities.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
}
