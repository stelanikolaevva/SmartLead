package com.demoprojects.smartlead.service;

import com.demoprojects.smartlead.exception.DuplicatedMessageException;
import com.demoprojects.smartlead.model.Message;
import com.demoprojects.smartlead.model.dto.MessageDTO;
import com.demoprojects.smartlead.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDTO> getAllMessages() {
        List<Message> allMessages = messageRepository.findAll();
        log.info("Getting all messages. Count: {}", allMessages.size());

        return allMessages.stream()
                .map(message -> new MessageDTO(message.getContent()))
                .toList();
    }

    public MessageDTO postMessage(MessageDTO message) {
        log.info("Posting new message: {}", message);

        boolean isAlreadyAsked = messageRepository.existsByContent(message.content());
        if (isAlreadyAsked) {
            throw new DuplicatedMessageException("Message with content \"" + message.content() + "\" already exists!");
        }

        Message newMessage = new Message(message.content());

        Message saved = messageRepository.save(newMessage);
        log.info("Message with id {} successfully saved.", saved.getId());
        return message;
    }
}
