package team.themoment.hellogsmassignment.domain.oneseo.dto;

import lombok.Builder;

@Builder
public record SearchOneseoPageInfoDto(

        Integer totalPages,
        Long totalElements
) {
}
