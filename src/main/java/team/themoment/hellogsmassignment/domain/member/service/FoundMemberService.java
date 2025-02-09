package team.themoment.hellogsmassignment.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmassignment.domain.member.dto.response.FoundMemberResDto;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.member.repo.MemberRepository;

@Service
@RequiredArgsConstructor
public class FoundMemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public FoundMemberResDto execute(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);

        return FoundMemberResDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .birth(member.getBirth())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }

}
