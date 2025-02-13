package team.themoment.hellogsmassignment.domain.oneseo.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchOneseosResDto(
        SearchOneseoPageInfoDto info,
        List<SearchOneseoResDto> oneseos
) {
}
