package team.themoment.hellogsmassignment.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.themoment.hellogsmassignment.domain.member.dto.response.FoundMemberResDto;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.member.repo.MemberRepository;
import team.themoment.hellogsmassignment.domain.member.service.FoundMemberService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Member 조회 Service 클래스의")
public class FoundMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private FoundMemberService foundMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메서드는")
    class Describe_execute {

        private final Long memberId = 1L;

        @Nested
        @DisplayName("Member ID가 주어졌을 때")
        class Context_with_existing_member_id {
            private Member member;

            @BeforeEach
            void setUp() {
                // given
                member = Member.builder()
                        .id(memberId)
                        .email("email@email.com")
                        .name("김겸비")
                        .birth(LocalDate.of(2009, 3, 28))
                        .phoneNumber("01012345678")
                        .build();

                given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            }

            @Test
            @DisplayName("Member를 조회하여 적절한 ResDTO를 반환한다.")
            void it_query_member_response_resDto() {
                // when
                FoundMemberResDto resDto = foundMemberService.execute(member.getId());

                // then
                assertEquals(member.getId(), memberId);
                assertEquals(member.getName(), resDto.name());
                assertEquals(member.getPhoneNumber(), resDto.phoneNumber());
                assertEquals(member.getBirth(), resDto.birth());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 Member ID가 주어졌을 때")
        class Context_with_non_existing_member_id {

            @BeforeEach
            void setUp() {
                // given
                given(memberRepository.findById(memberId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("Member ID 찾을 수 없음 예외를 던진다.")
            void it_throws_not_found_member_id_exception() {
                // when, then
                assertThrows(RuntimeException.class, () -> foundMemberService.execute(memberId));
            }
        }
    }
}
