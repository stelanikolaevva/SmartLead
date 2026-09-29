package com.demoprojects.smartlead.userMessage;

import com.demoprojects.smartlead.common.error.exceptions.DuplicatedMessageException;
import com.demoprojects.smartlead.userMessage.dto.UserMessageRequest;
import com.demoprojects.smartlead.userMessage.dto.UserMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserMessagesService {

    private final UserMessagesRepository userMessagesRepository;
    private final UserMessageMapper mapper;

    public List<UserMessageResponse> getAllMessages() {
        List<UserMessage> allUserMessages = userMessagesRepository.findAll();
        log.info("Getting all messages. Count: {}", allUserMessages.size());

        return allUserMessages.stream()
                .map(mapper::mapToDto)
                .toList();
    }

    public UserMessageResponse postMessage(UserMessageRequest message) {
        log.info("Posting new message: {}", message);

        boolean isAlreadyAsked = userMessagesRepository.existsByContent(message.content());
        if (isAlreadyAsked) {
            log.warn("Message with content: {} already exists", message.content());
            throw new DuplicatedMessageException("Duplicated message!");
        }
        UserMessage saved = userMessagesRepository.save(new UserMessage(message.content()));

        log.info("Message with id {} successfully saved.", saved.getId());
        return mapper.mapToDto(saved);
    }

}
