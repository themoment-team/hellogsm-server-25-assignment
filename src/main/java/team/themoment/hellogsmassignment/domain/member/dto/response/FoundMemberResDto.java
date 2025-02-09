package team.themoment.hellogsmassignment.domain.member.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FoundMemberResDto(
        Long memberId,
        String name,
        String phoneNumber,
        LocalDate birth
) {
}
