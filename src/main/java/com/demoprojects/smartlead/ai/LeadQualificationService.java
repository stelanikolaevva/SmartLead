package com.demoprojects.smartlead.ai;

import com.demoprojects.smartlead.ai.dto.ChatMessage;
import com.demoprojects.smartlead.ai.dto.ChatRequest;
import com.demoprojects.smartlead.ai.dto.ChatResponse;
import com.demoprojects.smartlead.ai.dto.LeadClassificationResponse;
import com.demoprojects.smartlead.common.client.HuggingFaceClient;
import com.demoprojects.smartlead.common.error.exceptions.UserMessageNotFoundException;
import com.demoprojects.smartlead.lead.Lead;
import com.demoprojects.smartlead.lead.LeadRepository;
import com.demoprojects.smartlead.userMessage.Status;
import com.demoprojects.smartlead.userMessage.UserMessage;
import com.demoprojects.smartlead.userMessage.UserMessagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeadQualificationService {

    private final HuggingFaceClient client;

    private final LeadRepository leadRepository;
    private final UserMessagesRepository userMessagesRepository;

    private final ObjectMapper objectMapper;

    @Value("${ai.huggingface.model}")
    private String aiModel;


    @Async
    public void checkIfMessageQualifies(Long messageId) {
        UserMessage userMessage = userMessagesRepository.findById(messageId)
                .orElseThrow(() -> new UserMessageNotFoundException("Message not found: " + messageId));

        String promptMessage;
        try {
            promptMessage = Files.readString(Path.of("src/main/resources/data/prompt.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e); //ToDo make it custom exception or a find better way to read it
        }
        log.info("Calling huggingFace for message id: {}", userMessage.getId());

        Status userMessageStatus;
        try {
            LeadClassificationResponse potentialLead = callClient(promptMessage, userMessage.getContent());

            if (potentialLead.isLead()) {
                Lead lead = buildLeadFromResponse(potentialLead);
                leadRepository.save(lead);

                userMessageStatus = Status.QUALIFIED;
            } else {
                userMessageStatus = Status.UNQUALIFIED;
            }
        } catch (Exception e) {
            log.error("Lead Qualification failed for message ID: {}", userMessage.getId(), e);
            userMessageStatus = Status.QUALIFICATION_FAILED;
        }

        updateUserMessageWithStatus(messageId, userMessageStatus);
    }

    private LeadClassificationResponse callClient(String promptMessage, String userMessageContent) {
        ChatRequest request = new ChatRequest(
                aiModel,
                List.of(new ChatMessage("system", promptMessage),
                        new ChatMessage("user", userMessageContent)),
                false);

        ChatResponse chatResponse = client.completion(request);
        log.info("HuggingFace response: {}", chatResponse);

        return objectMapper.readValue(chatResponse.content(), LeadClassificationResponse.class);
    }

    private void updateUserMessageWithStatus(Long messageId, Status status) {
        UserMessage userMessage = userMessagesRepository.findById(messageId)
                .orElseThrow(() -> new UserMessageNotFoundException("Message not found: " + messageId));

        log.info("Message with id {} has status: {}", userMessage.getId(), status.name());
        userMessage.setStatus(status);
        userMessagesRepository.save(userMessage);
    }

    private Lead buildLeadFromResponse(LeadClassificationResponse response) {
        return new Lead(
                response.title(),
                response.type(),
                response.urgency(),
                response.summary()
        );
    }

}
