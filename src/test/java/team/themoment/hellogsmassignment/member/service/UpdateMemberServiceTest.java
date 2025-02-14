package team.themoment.hellogsmassignment.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.themoment.hellogsmassignment.domain.member.dto.request.UpdateMemberReqDto;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.member.repo.MemberRepository;
import team.themoment.hellogsmassignment.domain.member.service.FoundMemberService;
import team.themoment.hellogsmassignment.domain.member.service.UpdateMemberService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("Member 업데이트 Service 클래스의")
public class UpdateMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private UpdateMemberService updateMemberService;

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

        private final Member member = new Member(
                1L,
                "huimail@email.com",
                "신희성",
                LocalDate.of(2007, 3, 1),
                "01038157596"

        );

        private final UpdateMemberReqDto reqDto = new UpdateMemberReqDto(
                "김겸비",
                "email@email.com",
                "01012345678",
                LocalDate.of(2007, 5, 17)
        );

        @Nested
        @DisplayName("Member 업데이트 DTO 객체가 주어졌을 때")
        class Context_with_member_update_DTO {

            @BeforeEach
            void setUp() {
                // given
                given(memberRepository.existsByEmail(reqDto.getEmail())).willReturn(false);
                given(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).willReturn(false);
                given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            }

            @Test
            @DisplayName("DTO 객체의 정보에 따라 Member를 업데이트하여 save 한다.")
            void it_update_member_info_about_dto_and_save() {
                // when
                updateMemberService.execute(memberId, reqDto);

                // then
                ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
                verify(memberRepository).save(memberCaptor.capture());
                Member capturedMember = memberCaptor.getValue();

                assertEquals(memberId, capturedMember.getId());
                assertEquals(reqDto.getName(), capturedMember.getName());
                assertEquals(reqDto.getPhoneNumber(), capturedMember.getPhoneNumber());
                assertEquals(reqDto.getBirth(), capturedMember.getBirth());
                assertEquals(reqDto.getEmail(), capturedMember.getEmail());
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
                // when + then
                assertThrows(RuntimeException.class, () -> foundMemberService.execute(memberId));
            }
        }

        @Nested
        @DisplayName("중복된 Email이 주어졌을 때")
        class Context_with_existing_email {

            @BeforeEach
            void setUp() {
                // given
                given(memberRepository.existsByEmail(reqDto.getEmail())).willReturn(true);
            }

            @Test
            @DisplayName("Email 중복 예외를 던진다.")
            void it_throws_email_existing_exception() {
                // when + then
                assertThrows(RuntimeException.class, () -> updateMemberService.execute(memberId, reqDto));
            }
        }

        @Nested
        @DisplayName("중복된 PhoneNumber가 주어졌을 때")
        class Context_with_existingd_phone_number {

            @BeforeEach
            void setUp() {
                // given
                given(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).willReturn(true);
            }

            @Test
            @DisplayName("PhoneNumber 중복 예외를 던진다.")
            void it_throws_phone_number_existing_exception() {
                // when + then
                assertThrows(RuntimeException.class, () -> updateMemberService.execute(memberId, reqDto));
            }
        }
    }
}
