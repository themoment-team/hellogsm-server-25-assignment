package team.themoment.hellogsmassignment.domain.member.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsmassignment.domain.member.dto.request.CreateMemberReqDto;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.member.repo.MemberRepository;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class Member_생성_Service_클래스의 {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CreateMemberService createMemberService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class execute_메서드는 {


        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class Member_생성_DTO_객체가_주어졌을_때 {

            @Test
            @DisplayName("DTO 객체의 정보에 따라 Member를 생성하여 save 한다.")
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

                // when
                createMemberService.execute(reqDto);

                // then
                verify(memberRepository).existsByEmail(reqDto.getEmail());
                verify(memberRepository).existsByPhoneNumber(reqDto.getPhoneNumber());
                verify(memberRepository).save(any(Member.class));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복된_Email이_주어졌을_때 {

            @Test
            @DisplayName("Email 중복 예외를 던진다.")
            void it_throws_exception_when_email_is_duplicated() {
                // given
                CreateMemberReqDto reqDto = new CreateMemberReqDto(
                        "홍길동",
                        "s00001@@gsm.hs.kr",
                        "010-1234-5678",
                        LocalDate.now()
                );

                // when
                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(true);

                // then
                Assertions.assertThrows(RuntimeException.class, () -> createMemberService.execute(reqDto));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복된_PhoneNumber가_주어졌을_때 {

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

                // when
                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(false);
                when(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).thenReturn(true);

                // then
                Assertions.assertThrows(RuntimeException.class, () -> createMemberService.execute(reqDto));
            }
        }
    }
}