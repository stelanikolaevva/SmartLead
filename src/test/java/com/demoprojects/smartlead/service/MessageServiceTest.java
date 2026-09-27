package com.demoprojects.smartlead.service;

import com.demoprojects.smartlead.model.Message;
import com.demoprojects.smartlead.model.dto.MessageDTO;
import com.demoprojects.smartlead.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;


    @Test
    void givenMockMessages_whenRetrievingAllMessages_shouldReturnSameMessageDtos() {
        //given

        when(messageRepository.findAll()).thenReturn(getMockMessages());

        //when
        List<MessageDTO> actual = messageService.getAllMessages();

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(getMessageDTOs());
        verify(messageRepository, times(1)).findAll();
    }

    @Test
    void postMessage() {
        //given
        MessageDTO mockDto = new MessageDTO("MockMessage");

        //when
       messageService.postMessage(mockDto);

        //then
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository,times(1)).save(captor.capture());

        Message actual = captor.getValue();
        assertThat(actual.getContent()).isEqualTo("MockMessage");
    }

    private List<Message> getMockMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Message"));
        messages.add(new Message("Message2"));
        messages.add(new Message("Message3"));
        return messages;
    }

    private List<MessageDTO> getMessageDTOs() {
        List<MessageDTO> messages = new ArrayList<>();
        messages.add(new MessageDTO("Message"));
        messages.add(new MessageDTO("Message2"));
        messages.add(new MessageDTO("Message3"));
        return messages;
    }
}