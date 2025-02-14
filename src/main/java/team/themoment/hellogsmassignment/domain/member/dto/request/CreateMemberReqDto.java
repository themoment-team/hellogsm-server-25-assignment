package team.themoment.hellogsmassignment.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateMemberReqDto {
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birth;
}
