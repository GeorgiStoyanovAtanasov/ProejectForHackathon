package com.example.demo.Repositories;

import com.example.demo.Entities.Message;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderAndReceiver(User sender, User receiver);
}
