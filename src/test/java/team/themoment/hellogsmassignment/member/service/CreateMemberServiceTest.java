package team.themoment.hellogsmassignment.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.themoment.hellogsmassignment.domain.member.dto.request.CreateMemberReqDto;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.member.repo.MemberRepository;
import team.themoment.hellogsmassignment.domain.member.service.CreateMemberService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("Member 생성 Service 클래스의")
public class CreateMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CreateMemberService createMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메서드는")
    class Describe_execute {

        private final CreateMemberReqDto reqDto = new CreateMemberReqDto(
                "김겸비",
                "email@email.com",
                "01012345678",
                LocalDate.of(2007, 5, 17)
        );

        @Nested
        @DisplayName("Member 생성 DTO 객체가 주어졌을 때")
        class Context_with_member_create_DTO {

            @BeforeEach
            void setUp() {
                given(memberRepository.existsByEmail(reqDto.getEmail())).willReturn(false);
                given(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).willReturn(false);
            }

            @Test
            @DisplayName("DTO 객체의 정보에 따라 Member를 생성하여 save 한다.")
            void it_create_member() {
                createMemberService.execute(reqDto);
                ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
                verify(memberRepository).save(memberCaptor.capture());
                Member capturedMember = memberCaptor.getValue();

                assertEquals(reqDto.getName(), capturedMember.getName());
                assertEquals(reqDto.getPhoneNumber(), capturedMember.getPhoneNumber());
                assertEquals(reqDto.getBirth(), capturedMember.getBirth());
                assertEquals(reqDto.getEmail(), capturedMember.getEmail());
            }
        }

        @Nested
        @DisplayName("중복된 Email이 주어졌을 때")
        class Context_with_existing_email {

            @BeforeEach
            void setUp() {
                given(memberRepository.existsByEmail(reqDto.getEmail())).willReturn(true);
            }

            @Test
            @DisplayName("Email 중복 예외를 던진다.")
            void it_throws_email_existing_exception() {
                assertThrows(RuntimeException.class, () -> createMemberService.execute(reqDto));
            }
        }

        @Nested
        @DisplayName("중복된 PhoneNumber가 주어졌을 때")
        class Context_with_existingd_phone_number {

            @BeforeEach
            void setUp() {
                given(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).willReturn(true);
            }

            @Test
            @DisplayName("PhoneNumber 중복 예외를 던진다.")
            void it_throws_phone_number_existing_exception() {
                assertThrows(RuntimeException.class, () -> createMemberService.execute(reqDto));
            }
        }
    }
}
