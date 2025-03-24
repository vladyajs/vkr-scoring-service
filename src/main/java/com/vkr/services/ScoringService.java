package com.vkr.services;

import com.vkr.models.LoanApplication;
import com.vkr.models.ScoringResult;
import com.vkr.repositories.ScoringResultRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ScoringService {

    private CalcService calcService;

    public ScoringResult calculateScoring(LoanApplication loanApplication) {
        log.info("save scoring result");
        return calcService.calculateScore(loanApplication);
    }


}
