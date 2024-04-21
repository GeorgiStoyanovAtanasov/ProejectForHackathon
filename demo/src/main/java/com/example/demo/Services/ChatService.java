package com.example.demo.Services;

import com.example.demo.Entities.Message;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesBetweenUsers(User sender, User receiver) {
        List<Message> firstMessages = messageRepository.findAllBySenderAndReceiver(sender, receiver);
        List<Message> secondMessages = messageRepository.findAllBySenderAndReceiver(receiver, sender);

        // Combine the two lists
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(firstMessages);
        allMessages.addAll(secondMessages);

        // Sort the combined list based on timestamp
        Collections.sort(allMessages, (m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        return allMessages;
    }

    public void saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }
}
