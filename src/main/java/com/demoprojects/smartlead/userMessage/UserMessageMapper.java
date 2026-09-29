package com.demoprojects.smartlead.userMessage;

import com.demoprojects.smartlead.userMessage.dto.UserMessageResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserMessageMapper {

    UserMessageResponse mapToDto(UserMessage response);
}
