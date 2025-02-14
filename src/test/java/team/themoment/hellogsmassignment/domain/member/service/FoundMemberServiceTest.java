package team.themoment.hellogsmassignment.domain.member.service;

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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("execute 메소드는")
    class Describe_execute {
        private final Long memberId = 1L;
        @Nested
        @DisplayName("Member ID가 주어졌을 때")
        class Context_with_member_id {
            private final Member member = Member.builder()
                    .id(memberId)
                    .name("홍길동")
                    .email("test@example.com")
                    .phoneNumber("01012345678")
                    .birth(LocalDate.of(2000, 1, 1))
                    .build();
            @BeforeEach
            void setUp(){
                given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            }
            /**
             * @given 존재하는 memeberId가 주어졌을 때
             * @when FoundMemberService의 execute를 실행하면
             * @then Member를 조회하여 FoundMemberResDto를 반환한다.
             */
            @Test
            @DisplayName("Member를 조회하여 적절한 ResDTO를 반환한다.")
            void it_returns_resDto(){
                FoundMemberResDto result = foundMemberService.execute(memberId);
                assertEquals(result.memberId(),memberId);
                assertEquals(result.name(),member.getName());
                assertEquals(result.birth(),member.getBirth());
                assertEquals(result.phoneNumber(),member.getPhoneNumber());
            }
        }
        @Nested
        @DisplayName("존재하지 않는 Member ID가 주어졌을 때")
        class Context_with_not_found_member_id {
            @BeforeEach
            void setUp(){
                given(memberRepository.findById(memberId)).willReturn(Optional.empty());
            }
            /**
             * @given 존재하지 않는 memeberId가 주어졌을 때
             * @when FoundMemberService의 execute를 실행하면
             * @then RuntimeException을 던진다.
             */
            @Test
            @DisplayName("Member ID 찾을 수 없음 예외를 던진다.")
            void it_throws_runtime_exception(){
                assertThrows(RuntimeException.class,()->foundMemberService.execute(memberId));
            }
        }
    }
}
