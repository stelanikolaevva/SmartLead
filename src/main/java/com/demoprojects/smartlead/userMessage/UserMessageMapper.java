package com.demoprojects.smartlead.userMessage;

import com.demoprojects.smartlead.userMessage.dto.UserMessageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMessageMapper {

    UserMessageResponse mapToDto(UserMessage response);
}
