package team.themoment.hellogsmassignment.domain.member.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsmassignment.domain.member.dto.request.CreateMemberReqDto;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.member.repo.MemberRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member 생성 Service 클래스의")
class CreateMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CreateMemberService createMemberService;

    @Nested
    @DisplayName("execute 메서드는")
    class describe_execute {

        @Nested
        @DisplayName("Member 생성 DTO 객체가 주어졌을 때")
        class context_with_create_member_dto {

            @Test
            @DisplayName("DTO 객체의 정보에 따라 Member를 생성하여 저장한다.")
            void it_saves_member_according_to_dto() {
                // given
                CreateMemberReqDto reqDto = new CreateMemberReqDto(
                        "홍길동",
                        "s00001@gsm.hs.kr",
                        "010-1234-5678",
                        LocalDate.now()
                );

                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(false);
                when(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).thenReturn(false);

                ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);

                // when
                createMemberService.execute(reqDto);

                // then
                verify(memberRepository).existsByEmail(reqDto.getEmail());
                verify(memberRepository).existsByPhoneNumber(reqDto.getPhoneNumber());
                verify(memberRepository).save(any(Member.class));

                assertEquals(reqDto.getName(), memberCaptor.getValue().getName());
                assertEquals(reqDto.getEmail(), memberCaptor.getValue().getEmail());
                assertEquals(reqDto.getPhoneNumber(), memberCaptor.getValue().getPhoneNumber());
                assertEquals(reqDto.getBirth(), memberCaptor.getValue().getBirth());
            }
        }

        @Nested
        @DisplayName("중복된 Email이 주어졌을 때")
        class context_with_duplicate_email {

            @Test
            @DisplayName("Email 중복 예외를 던진다.")
            void it_throws_exception_when_email_is_duplicated() {
                // given
                CreateMemberReqDto reqDto = new CreateMemberReqDto(
                        "홍길동",
                        "s00001@gsm.hs.kr",
                        "010-1234-5678",
                        LocalDate.now()
                );

                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(true);

                // when & then
                Assertions.assertThrows(RuntimeException.class, () -> createMemberService.execute(reqDto));
                verify(memberRepository).existsByEmail(reqDto.getEmail());
            }
        }

        @Nested
        @DisplayName("중복된 PhoneNumber가 주어졌을 때")
        class context_with_duplicate_phone_number {

            @Test
            @DisplayName("PhoneNumber 중복 예외를 던진다.")
            void it_throws_exception_when_phone_number_is_duplicated() {
                // given
                CreateMemberReqDto reqDto = new CreateMemberReqDto(
                        "홍길동",
                        "s00001@gsm.hs.kr",
                        "010-1234-5678",
                        LocalDate.now()
                );

                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(false);
                when(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).thenReturn(true);

                // when & then
                Assertions.assertThrows(RuntimeException.class, () -> createMemberService.execute(reqDto));
                verify(memberRepository).existsByPhoneNumber(reqDto.getPhoneNumber());
            }
        }
    }
}