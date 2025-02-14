package team.themoment.hellogsmassignment.domain.member.service;

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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@DisplayName("CrateMemberService 클래스의")
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
    @DisplayName("execute 메소드는")
    class Describe_execute {
        private final CreateMemberReqDto createMemberReqDto = CreateMemberReqDto.builder()
                .name("홍길동")
                .email("test@example.com")
                .phoneNumber("01012345678")
                .birth(LocalDate.of(2000, 1, 1))
                .build();
        @Nested
        @DisplayName("Member 생성 DTO 객체가 주어졌을 때")
        class Context_with_CreateMemberReqDto {
            /**
             * @given 올바른 CreateMemberReqDto가 주어졌을때
             * @when CreateMemberService의 execute메서드를 실행하면
             * @then Dto의 정보에 따라 Member를 생성하고 save 한다.
             */
            @Test
            @DisplayName("DTO 객체의 정보에 따라 Member를 생성하여 save 한다.")
            void it_creates_and_saves_member() {
                //given: 올바른 CreateMemberReqDto가 주어졌을때
                given(memberRepository.existsByEmail(createMemberReqDto.getEmail())).willReturn(false);
                given(memberRepository.existsByEmail(createMemberReqDto.getPhoneNumber())).willReturn(false);

                //when: CreateMemberService의 execute메서드를 실행하면
                createMemberService.execute(createMemberReqDto);

                //then: Dto의 정보에 따라 Member를 생성하고 save 한다.
                ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
                verify(memberRepository).save(captor.capture());
                Member member = captor.getValue();
                assertEquals(createMemberReqDto.getName(), member.getName());
            }
        }
        @Nested
        @DisplayName("중복된 Email이 주어졌을 때")
        class Context_with_duplicate_email {
            @BeforeEach
            void setUp(){
                given(memberRepository.existsByEmail(createMemberReqDto.getEmail())).willReturn(true);
            }
            /**
             * @given emial이 중복된 CreateMemberReqDto가 주어졌을때
             * @when CreateMemberService의 execute메서드를 실행하면
             * @then RuntimeException을 던진다.
             */
            @Test
            @DisplayName("RuntimeException을 던진다.")
            void it_throws_runtime_exception() {
                //given: emial이 중복된 CreateMemberReqDto가 주어졌을때
                given(memberRepository.existsByEmail(createMemberReqDto.getEmail())).willReturn(true);

                //then: RuntimeException을 던진다.
                assertThrows(RuntimeException.class,
                        //when: CreateMemberService의 execute메서드를 실행하면
                        () -> createMemberService.execute(createMemberReqDto)
                );
            }
        }
        @Nested
        @DisplayName("중복된 PhoneNumber가 주어졌을 때")
        class Context_with_duplicate_phone_number {
            @BeforeEach
            void setUp(){
                given(memberRepository.existsByPhoneNumber(createMemberReqDto.getPhoneNumber())).willReturn(true);
            }
            /**
             * @given phoneNumber가 중복된 CreateMemberReqDto가 주어졌을때
             * @when CreateMemberService의 execute메서드를 실행하면
             * @then RuntimeException을 던진다.
             */
            @Test
            @DisplayName("RuntimeException을 던진다.")
            void it_throws_runtime_exception() {
                //given: phoneNumber가 중복된 CreateMemberReqDto가 주어졌을때
                given(memberRepository.existsByPhoneNumber(createMemberReqDto.getPhoneNumber())).willReturn(true);

                //then: RuntimeException을 던진다.
                assertThrows(RuntimeException.class,
                        //when: CreateMemberService의 execute메서드를 실행하면
                        () -> createMemberService.execute(createMemberReqDto)
                );
            }
        }
    }
}
