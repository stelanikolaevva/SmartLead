package com.demoprojects.smartlead.userMessage;

import com.demoprojects.smartlead.ai.LeadQualificationService;
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
    private final LeadQualificationService leadQualificationService;
    private final UserMessageMapper mapper;

    public List<UserMessageResponse> getAllMessages() {
        List<UserMessage> allUserMessages = userMessagesRepository.findAll();
        log.info("Getting all messages. Count: {}", allUserMessages.size());

        return allUserMessages.stream()
                .map(mapper::mapToDto)
                .toList();
    }

    public UserMessageResponse postMessage(UserMessageRequest request) {
        log.info("Posting new message: {}", request);

        boolean isAlreadyAsked = userMessagesRepository.existsByContent(request.content());
        if (isAlreadyAsked) {
            log.warn("Message with content {} already exists", request.content());
            throw new DuplicatedMessageException("Duplicated message content!");
        }

        UserMessage saved = userMessagesRepository.save(new UserMessage(request.content()));

        leadQualificationService.checkIfMessageQualifies(saved.getId());

        log.info("Message with id {} successfully saved.", saved.getId());
        return new UserMessageResponse(saved.getId(), saved.getContent(), saved.getStatus());
    }

}
