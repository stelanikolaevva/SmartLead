package com.demoprojects.smartlead.controller;

import com.demoprojects.smartlead.model.dto.MessageDTO;
import com.demoprojects.smartlead.service.MessageService;
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
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@Valid @RequestBody MessageDTO message) {
        MessageDTO postedMessage = messageService.postMessage(message);

        return ResponseEntity.status(HttpStatus.CREATED).body(postedMessage);
    }
}
