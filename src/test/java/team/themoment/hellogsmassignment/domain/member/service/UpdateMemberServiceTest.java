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
@DisplayName("Member 업데이트 Service 클래스의")
class UpdateMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private UpdateMemberService updateMemberService;

    @Nested
    @DisplayName("execute 메서드는")
    class describe_execute {

        @Nested
        @DisplayName("Member 업데이트 DTO 객체가 주어졌을 때")
        class context_when_update_member_dto_provided {

            @Test
            @DisplayName("DTO 객체의 정보에 따라 Member의 정보를 업데이트하여 save 한다.")
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
        @DisplayName("존재하지 않는 Member ID가 주어졌을 때")
        class context_when_member_id_does_not_exist {

            @Test
            @DisplayName("Member ID 찾을 수 없음 예외를 던진다.")
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
        @DisplayName("중복된 Email이 주어졌을 때")
        class context_when_duplicate_email_provided {

            @Test
            @DisplayName("Email 중복 예외를 던진다.")
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
        @DisplayName("중복된 PhoneNumber가 주어졌을 때")
        class context_when_duplicated_phone_number_provided {

            @Test
            @DisplayName("PhoneNumber 중복 예외를 던진다.")
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