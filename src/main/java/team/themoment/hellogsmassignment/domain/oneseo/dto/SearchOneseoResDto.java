package team.themoment.hellogsmassignment.domain.oneseo.dto;

import lombok.Builder;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.YesNo;

import java.math.BigDecimal;

@Builder
public record SearchOneseoResDto(

        Long memberId,
        String submitCode,
        YesNo realOneseoArrivedYn,
        String name,
        Screening screening,
        String schoolName,
        String phoneNumber,
        String guardianPhoneNumber,
        String schoolTeacherPhoneNumber,
        YesNo firstTestPassYn,
        BigDecimal aptitudeEvaluationScore,
        BigDecimal interviewScore,
        YesNo secondTestPassYn,
        YesNo entranceIntentionYn
) {
}
