package com.demoprojects.smartlead.repository;

import com.demoprojects.smartlead.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    boolean existsByContent(String content);
}
