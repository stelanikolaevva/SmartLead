package com.demoprojects.smartlead.userMessage;

import com.demoprojects.smartlead.userMessage.dto.UserMessageRequest;
import com.demoprojects.smartlead.userMessage.dto.UserMessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class UserMessagesController {

    private final UserMessagesService userMessagesService;

    @GetMapping
    public ResponseEntity<List<UserMessageResponse>> getAllMessages() {
        return ResponseEntity.ok(userMessagesService.getAllMessages());
    }

    @PostMapping
    public ResponseEntity<UserMessageResponse> sendMessage(@Valid @RequestBody UserMessageRequest message) {
        UserMessageResponse postedMessage = userMessagesService.postMessage(message);

        return ResponseEntity.status(HttpStatus.CREATED).body(postedMessage);
    }
}
