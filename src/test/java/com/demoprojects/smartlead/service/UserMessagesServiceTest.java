package com.demoprojects.smartlead.service;

import com.demoprojects.smartlead.common.error.exceptions.DuplicatedMessageException;
import com.demoprojects.smartlead.userMessage.UserMessage;
import com.demoprojects.smartlead.userMessage.UserMessageMapper;
import com.demoprojects.smartlead.userMessage.UserMessagesRepository;
import com.demoprojects.smartlead.userMessage.UserMessagesService;
import com.demoprojects.smartlead.userMessage.dto.UserMessageRequest;
import com.demoprojects.smartlead.userMessage.dto.UserMessageResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMessagesServiceTest {

    private static final String CONTENT = "I would like a demo of your enterprise plan";

    @Mock
    private UserMessagesRepository userMessagesRepository;
    @Mock
    private UserMessageMapper mapper;

    @InjectMocks
    private UserMessagesService userMessagesService;


    @Test
    void shouldReturnAllUserMessages() {
        UserMessage first = new UserMessage("first");
        UserMessage second = new UserMessage("second");
        UserMessageResponse firstDto = mock(UserMessageResponse.class);
        UserMessageResponse secondDto = mock(UserMessageResponse.class);

        when(userMessagesRepository.findAll()).thenReturn(List.of(first, second));
        when(mapper.mapToDto(first)).thenReturn(firstDto);
        when(mapper.mapToDto(second)).thenReturn(secondDto);

        List<UserMessageResponse> result = userMessagesService.getAllMessages();

        assertThat(result).containsExactly(firstDto, secondDto);
    }


    @Test
    void shouldReturnNoUserMessages() {
        when(userMessagesRepository.findAll()).thenReturn(List.of());

        List<UserMessageResponse> result = userMessagesService.getAllMessages();

        assertThat(result).isEmpty();
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldSaveNewMessage() {
        UserMessageRequest request = new UserMessageRequest(CONTENT);
        UserMessage saved = new UserMessage(CONTENT);
        UserMessageResponse expected = mock(UserMessageResponse.class);

        when(userMessagesRepository.existsByContent(CONTENT)).thenReturn(false);
        when(userMessagesRepository.save(any(UserMessage.class))).thenReturn(saved);
        when(mapper.mapToDto(saved)).thenReturn(expected);

        UserMessageResponse result = userMessagesService.postMessage(request);

        assertThat(result).isSameAs(expected);
    }

    @Test
    void shouldSaveMessageWithRequestContent() {
        UserMessageRequest request = new UserMessageRequest(CONTENT);
        when(userMessagesRepository.existsByContent(CONTENT)).thenReturn(false);
        when(userMessagesRepository.save(any(UserMessage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        userMessagesService.postMessage(request);

        ArgumentCaptor<UserMessage> captor = ArgumentCaptor.forClass(UserMessage.class);
        verify(userMessagesRepository).save(captor.capture());
        assertThat(captor.getValue().getContent()).isEqualTo(CONTENT);
    }

    @Test
    void shouldThrowDuplicatedMessageException() {
        UserMessageRequest request = new UserMessageRequest(CONTENT);
        when(userMessagesRepository.existsByContent(CONTENT)).thenReturn(true);

        assertThatThrownBy(() -> userMessagesService.postMessage(request))
                .isInstanceOf(DuplicatedMessageException.class)
                .hasMessage("Duplicated message!");

        verify(userMessagesRepository, never()).save(any());
        verifyNoInteractions(mapper);
    }
}