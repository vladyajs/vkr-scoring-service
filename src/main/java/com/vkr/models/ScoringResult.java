package com.vkr.models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ScoringResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoringResultId;

    @ManyToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

    private Integer score;
    private LocalDateTime scoringDate;
    private String scoringStatus;
}
