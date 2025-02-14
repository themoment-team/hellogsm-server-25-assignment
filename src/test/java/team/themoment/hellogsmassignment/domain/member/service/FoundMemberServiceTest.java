package team.themoment.hellogsmassignment.domain.member.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsmassignment.domain.member.dto.response.FoundMemberResDto;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.member.repo.MemberRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member 조회 Service 클래스의")
class FoundMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private FoundMemberService foundMemberService;

    @DisplayName("execute 메서드는")
    @Nested
    class describe_execute {

        @DisplayName("Member ID가 주어졌을 때")
        @Nested
        class exist_member_id {

            @DisplayName("Member를 조회하여 적절한 ResDTO를 반환한다.")
            @Test
            void it_should_return_correct_res_dto_when_member_exists() {
                // Given
                Long memberId = 1L;
                Member existingMember = Member.builder()
                        .id(memberId)
                        .name("성춘향")
                        .email("s00001@gsm.hs.kr")
                        .phoneNumber("010-1234-5678")
                        .birth(LocalDate.of(2000, 1, 1))
                        .build();

                when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.of(existingMember));

                // When
                FoundMemberResDto foundMemberResDto = foundMemberService.execute(memberId);

                // Then
                verify(memberRepository).findById(memberId);
                assertEquals(memberId, foundMemberResDto.memberId());
                assertEquals(existingMember.getName(), foundMemberResDto.name());
                assertEquals(existingMember.getBirth(), foundMemberResDto.birth());
                assertEquals(existingMember.getPhoneNumber(), foundMemberResDto.phoneNumber());
            }
        }

        @DisplayName("존재하지 않는 Member ID가 주어졌을 때")
        @Nested
        class non_exist_member_id {

            @DisplayName("Member ID 찾을 수 없음 예외를 던진다.")
            @Test
            void it_throws_member_id_not_found_exception() {
                // Given
                Long memberId = 1L;

                when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.empty());

                // When & Then
                assertThrows(RuntimeException.class, () -> foundMemberService.execute(memberId));
            }
        }
    }
}