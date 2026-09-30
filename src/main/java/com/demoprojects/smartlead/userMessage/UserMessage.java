package com.demoprojects.smartlead.userMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class UserMessage {
    @Id
    @SequenceGenerator(
            name="message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false,
            unique = true,
            columnDefinition = "TEXT")
    private String content;

    public UserMessage(String content) {
        this.content = content;
    }
}
