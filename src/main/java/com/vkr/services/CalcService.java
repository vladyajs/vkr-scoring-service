package com.vkr.services;

import com.vkr.models.LoanApplication;
import com.vkr.models.ScoringResult;
import com.vkr.repositories.ScoringResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CalcService {

    private final ScoringResultRepository scoringResultRepository;

    public ScoringResult calculateScore(LoanApplication loanApplication) {
        int score = 0;

        // Расчет баллов на основе возраста заявителя
        if (loanApplication.getApplicant() != null && loanApplication.getApplicant().getDateOfBirth()!=null) {
            int applicantAge = calculateAge(loanApplication.getApplicant().getDateOfBirth());
            if (applicantAge < 25) {
                score -= 50; // младше 25 лет — минус 50 баллов
            } else if (applicantAge > 60) {
                score -= 30; // старше 60 лет — минус 30 баллов
            } else {
                score += 20; // для среднего возраста — плюс 20 баллов
            }
        } else {
            score -= 100;
        }


        // Расчет баллов на основе суммы кредита
        BigDecimal loanAmount = loanApplication.getLoanAmount();
        if (loanAmount.compareTo(new BigDecimal("500000")) > 0) {
            score -= 50; // если сумма кредита больше 500 тыс. — минус 50 баллов
        } else if (loanAmount.compareTo(new BigDecimal("100000")) < 0) {
            score += 30; // если меньше 100 тыс. — плюс 30 баллов
        }

        // Расчет баллов на основе срока кредита
        int loanTerm = loanApplication.getLoanTerm();
        if (loanTerm > 5) {
            score -= 20; // если срок больше 5 лет — минус 20 баллов
        } else {
            score += 10; // если срок меньше 5 лет — плюс 10 баллов
        }

        // Расчет баллов на основе цели кредита
        String loanPurpose = loanApplication.getLoanPurpose();
        if (loanPurpose.equalsIgnoreCase("образование")) {
            score += 40; // если на образование — плюс 40 баллов
        } else if (loanPurpose.equalsIgnoreCase("потребление")) {
            score -= 20; // если на потребление — минус 20 баллов
        }

        // Создаем объект результата скоринга и сохраняем его в БД
        ScoringResult scoringResult = new ScoringResult();
        scoringResult.setLoanApplication(loanApplication);
        scoringResult.setScore(score);
        scoringResult.setScoringDate(LocalDateTime.now());
        scoringResult.setScoringStatus("Ожидает результата"); // можно установить статус "Ожидает результата" или изменить в зависимости от бизнес-логики
        //scoringResultRepository.save(scoringResult);

        return scoringResult;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
}
