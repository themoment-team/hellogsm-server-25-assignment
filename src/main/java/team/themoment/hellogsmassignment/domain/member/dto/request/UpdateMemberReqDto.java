package team.themoment.hellogsmassignment.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMemberReqDto {
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birth;
}
