package com.demoprojects.smartlead.userMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessagesRepository extends JpaRepository<UserMessage, Long> {

    boolean existsByContent(String content);
}
