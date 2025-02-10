package team.themoment.hellogsmassignment.domain.member.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsmassignment.domain.member.dto.request.UpdateMemberReqDto;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.member.repo.MemberRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class Member_업데이트_Service_클래스의 {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private UpdateMemberService updateMemberService;

    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @Nested
    class execute_메서드는 {

        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        @Nested
        class Member_업데이트_DTO_객체가_주어졌을_때 {

            @DisplayName("DTO 객체의 정보에 따라 Member의 정보를 업데이트하여 save 한다.")
            @Test
            void it_saves_member_according_to_dto() {
                // given
                UpdateMemberReqDto reqDto = new UpdateMemberReqDto(
                        "홍길동",
                        "s00002@gsm.hs.kr",
                        "010-1234-5678",
                        LocalDate.now()
                );
                Optional<Member> existingMember = Optional.ofNullable(
                        Member.builder()
                                .id(1L)
                                .name("성춘향")
                                .email("s00001@gsm.hs.kr")
                                .phoneNumber("010-1234-5678")
                                .birth(LocalDate.now())
                                .build()
                );

                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(false);
                when(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).thenReturn(false);
                when(memberRepository.findById(1L)).thenReturn(existingMember);

                // when
                updateMemberService.execute(1L, reqDto);

                // then
                verify(memberRepository).existsByEmail(reqDto.getEmail());
                verify(memberRepository).existsByPhoneNumber(reqDto.getPhoneNumber());
                verify(memberRepository).findById(1L);
                verify(memberRepository).save(argThat(member ->
                        member.getId().equals(1L) &&
                                member.getName().equals(reqDto.getName()) &&
                                member.getEmail().equals(reqDto.getEmail()) &&
                                member.getPhoneNumber().equals(reqDto.getPhoneNumber()) &&
                                member.getBirth().equals(reqDto.getBirth())
                ));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_Member_ID가_주어졌을_때 {

            @DisplayName("Member ID 찾을 수 없음 예외를 던진다.")
            @Test
            void it_throws_exception_when_member_id_does_not_exist() {
                // given
                UpdateMemberReqDto reqDto = new UpdateMemberReqDto(
                        "홍길동",
                        "s00002@gsm.hs.kr",
                        "010-1234-5678",
                        LocalDate.now()
                );

                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(false);
                when(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).thenReturn(false);
                when(memberRepository.findById(1L)).thenReturn(Optional.empty());

                // when, then
                Assertions.assertThrows(RuntimeException.class, () -> updateMemberService.execute(1L, reqDto));

                verify(memberRepository).existsByEmail(reqDto.getEmail());
                verify(memberRepository).existsByPhoneNumber(reqDto.getPhoneNumber());
                verify(memberRepository).findById(1L);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복된_Email이_주어졌을_때 {

            @DisplayName("Email 중복 예외를 던진다.")
            @Test
            void it_throws_exception_when_email_is_duplicated() {
                // given
                UpdateMemberReqDto reqDto = new UpdateMemberReqDto(
                        "홍길동",
                        "s00002@gsm.hs.kr",
                        "010-1234-5678",
                        LocalDate.now()
                );

                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(true);

                // when, then
                Assertions.assertThrows(RuntimeException.class, () -> updateMemberService.execute(1L, reqDto));

                verify(memberRepository).existsByEmail(reqDto.getEmail());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복된_PhoneNumber가_주어졌을_때 {

            @DisplayName("PhoneNumber 중복 예외를 던진다.")
            @Test
            void it_throws_exception_when_phone_number_is_duplicated() {
                // given
                UpdateMemberReqDto reqDto = new UpdateMemberReqDto(
                        "홍길동",
                        "s00002@gsm.hs.kr",
                        "010-1234-5678",
                        LocalDate.now()
                );

                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(false);
                when(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).thenReturn(true);

                // when, then
                Assertions.assertThrows(RuntimeException.class, () -> updateMemberService.execute(1L, reqDto));

                verify(memberRepository).existsByEmail(reqDto.getEmail());
                verify(memberRepository).existsByPhoneNumber(reqDto.getPhoneNumber());
            }
        }
    }
}