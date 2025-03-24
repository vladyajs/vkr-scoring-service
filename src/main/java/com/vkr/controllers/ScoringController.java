package com.vkr.controllers;

import com.vkr.models.LoanApplication;
import com.vkr.models.ScoringResult;
import com.vkr.services.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scoring")
@RequiredArgsConstructor
public class ScoringController {

    private final ScoringService scoringService;

    @PostMapping("/calculate")
    public ScoringResult calculateScore(@RequestBody LoanApplication loanApplication) {
        // Вызываем сервис для расчета скоринга
        return scoringService.calculateScoring(loanApplication);
    }
}
