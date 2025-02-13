package team.themoment.hellogsmassignment.domain.oneseo.dto;

import lombok.Builder;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.Screening;

@Builder
public record FoundOneseoResDto(
        Long oneseoId,
        String submitCode,
        Screening wantedScreening,
        DesiredMajorsResDto desiredMajors,
        OneseoPrivacyDetailResDto privacyDetail,
        OneseoTestResultResDto testResult
) {
}
