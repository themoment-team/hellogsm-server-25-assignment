package team.themoment.hellogsmassignment.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.hellogsmassignment.domain.member.dto.request.UpdateMemberReqDto;

import java.time.LocalDate;

@Getter
@Builder
@Entity
@Table(name = "tb_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    public void updateMember(UpdateMemberReqDto reqDto) {
        this.email = reqDto.getEmail();
        this.name = reqDto.getName();
        this.birth = reqDto.getBirth();
        this.phoneNumber = reqDto.getPhoneNumber();
    }

}
