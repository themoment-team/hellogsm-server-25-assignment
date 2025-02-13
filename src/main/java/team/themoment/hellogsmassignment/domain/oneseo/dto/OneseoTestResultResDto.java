package team.themoment.hellogsmassignment.domain.oneseo.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OneseoTestResultResDto(
    BigDecimal artsPhysicalSubjectsScore,
    BigDecimal totalSubjectsScore,
    BigDecimal totalNonSubjectsScore,
    BigDecimal documentEvaluationScore,
    BigDecimal aptitudeEvaluationScore,
    BigDecimal interviewScore
) {
}
