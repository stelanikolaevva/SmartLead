package com.demoprojects.smartlead.lead;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LeadType leadType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UrgencyLevel urgencyLevel;

    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;

    public Lead(String title, LeadType leadType, UrgencyLevel urgencyLevel, String summary) {
        this.title = title;
        this.leadType = leadType;
        this.urgencyLevel = urgencyLevel;
        this.summary = summary;
    }
}
