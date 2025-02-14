package team.themoment.hellogsmassignment.domain.member.service;

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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Member 업데이트 Service 클래스의")
public class UpdateMemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private UpdateMemberService updateMemberService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Nested
    @DisplayName("execute 메서드는")
    class Describe_execute {
        private final Long memberId = 1L;
        private final Member member = Member.builder()
                .id(memberId)
                .name("홍길동")
                .email("test@example.com")
                .phoneNumber("01012345678")
                .birth(LocalDate.of(2000, 1, 1))
                .build();
        private final UpdateMemberReqDto reqDto = UpdateMemberReqDto.builder()
                .name("홍길동2")
                .email("test@example.com2")
                .phoneNumber("01022226666")
                .birth(LocalDate.of(2010, 10, 10))
                .build();
        @Nested
        @DisplayName("Member 업데이트 DTO 객체가 주어졌을 때")
        class Context_with_member_update_dto {
            @BeforeEach
            void setUp(){
                when(memberRepository.existsByEmail(reqDto.getEmail())).thenReturn(false);
                given(memberRepository.existsByEmail(reqDto.getEmail())).willReturn(false);
                given(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).willReturn(false);
                given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            }
            /**
             * @given 올바른 UpdateMemberReqDto가 주어졌을 때
             * @when UpdateMemberService의 execute를 실행하면
             * @then Dto의 정보에 따라 Member를 업데이트하고 save 한다.
             */
            @Test
            @DisplayName("DTO 객체의 정보에 따라 Member의 정보를 업데이트하여 save 한다.")
            void it_updates_and_saves_member(){
                updateMemberService.execute(memberId,reqDto);
                ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
                verify(memberRepository).save(captor.capture());
                Member updatedMember = captor.getValue();
                assertEquals(updatedMember.getId(),memberId);
                assertEquals(updatedMember.getName(),reqDto.getName());
                assertEquals(updatedMember.getEmail(),reqDto.getEmail());
                assertEquals(updatedMember.getPhoneNumber(),reqDto.getPhoneNumber());
                assertEquals(updatedMember.getBirth(),reqDto.getBirth());
            }
        }
        @Nested
        @DisplayName("존재하지 않는 Member ID가 주어졌을 때")
        class Context_with_not_found_member_id {
            @BeforeEach
            void setUp(){
                given(memberRepository.existsByEmail(reqDto.getEmail())).willReturn(false);
                given(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).willReturn(false);
                given(memberRepository.findById(memberId)).willReturn(Optional.empty());
            }
            /**
             * @given 존재하지 않는 memberId를 가진 UpdateMemberReqDto가 주어졌을 때
             * @when UpdateMemberService의 execute를 실행하면
             * @then RuntimeException을 던진다.
             */
            @Test
            @DisplayName("Member ID 찾을 수 없음 예외를 던진다.")
            void it_throws_runtime_exception() {
                assertThrows(RuntimeException.class,()->updateMemberService.execute(memberId,reqDto));
            }
        }
        @Nested
        @DisplayName("중복된 Email이 주어졌을 때")
        class Context_with_duplicated_email {
            @BeforeEach
            void setUp(){
                given(memberRepository.existsByEmail(reqDto.getEmail())).willReturn(true);
                given(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).willReturn(false);
                given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            }
            /**
             * @given 중복된 email을 가진 UpdateMemberReqDto가 주어졌을 때
             * @when UpdateMemberService의 execute를 실행하면
             * @then RuntimeException을 던진다.
             */
            @Test
            @DisplayName("Email 중복 예외를 던진다.")
            void it_throws_runtime_exception() {
                assertThrows(RuntimeException.class,()->updateMemberService.execute(memberId,reqDto));
            }
        }
        @Nested
        @DisplayName("중복된 PhoneNumber가 주어졌을 때")
        class Context_with_duplicated_phone_number{
            @BeforeEach
            void setUp(){
                given(memberRepository.existsByEmail(reqDto.getEmail())).willReturn(false);
                given(memberRepository.existsByPhoneNumber(reqDto.getPhoneNumber())).willReturn(true);
                given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            }
            /**
             * @given 중복된 phoneNumber를 가진 UpdateMemberReqDto가 주어졌을 때
             * @when UpdateMemberService의 execute를 실행하면
             * @then RuntimeException을 던진다.
             */
            @Test
            @DisplayName("PhoneNumber 중복 예외를 던진다.")
            void it_throws_runtime_exception(){
                assertThrows(RuntimeException.class,()->updateMemberService.execute(memberId,reqDto));
            }
        }
    }
}
