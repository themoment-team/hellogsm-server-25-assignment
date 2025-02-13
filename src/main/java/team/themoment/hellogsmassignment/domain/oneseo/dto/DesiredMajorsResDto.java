package team.themoment.hellogsmassignment.domain.oneseo.dto;

import lombok.Builder;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.Major;

@Builder
public record DesiredMajorsResDto(
        Major firstDesiredMajor,
        Major secondDesiredMajor,
        Major thirdDesiredMajor
) {
}
