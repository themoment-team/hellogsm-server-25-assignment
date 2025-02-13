package team.themoment.hellogsmassignment.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.oneseo.dto.*;
import team.themoment.hellogsmassignment.domain.oneseo.entity.EntranceTestFactorsDetail;
import team.themoment.hellogsmassignment.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmassignment.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmassignment.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.ScreeningCategory;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmassignment.domain.oneseo.repository.OneseoJpaRepository;

@Service
@RequiredArgsConstructor
public class OneseoService {

    private final OneseoJpaRepository oneseoRepository;

    @Transactional(readOnly = true)
    public FoundOneseoResDto query(Long oneseoId) {
        Oneseo oneseo = oneseoRepository.findByIdFetchJoin(oneseoId)
                .orElseThrow(RuntimeException::new);
        Member member = oneseo.getMember();
        OneseoPrivacyDetail oneseoPrivacyDetail = oneseo.getOneseoPrivacyDetail();
        EntranceTestResult entranceTestResult = oneseo.getEntranceTestResult();
        EntranceTestFactorsDetail entranceTestFactorsDetail = entranceTestResult.getEntranceTestFactorsDetail();

        DesiredMajorsResDto desiredMajorsResDto = DesiredMajorsResDto.builder()
                .firstDesiredMajor(oneseo.getDesiredMajors().getFirstDesiredMajor())
                .secondDesiredMajor(oneseo.getDesiredMajors().getSecondDesiredMajor())
                .thirdDesiredMajor(oneseo.getDesiredMajors().getThirdDesiredMajor())
                .build();

        OneseoPrivacyDetailResDto oneseoPrivacyDetailResDto = OneseoPrivacyDetailResDto.builder()
                .name(member.getName())
                .birth(member.getBirth())
                .sex(member.getSex())
                .phoneNumber(member.getPhoneNumber())
                .graduationType(oneseoPrivacyDetail.getGraduationType())
                .detailAddress(oneseoPrivacyDetail.getDetailAddress())
                .graduationDate(oneseoPrivacyDetail.getGraduationDate())
                .guardianName(oneseoPrivacyDetail.getGuardianName())
                .address(oneseoPrivacyDetail.getAddress())
                .guardianPhoneNumber(oneseoPrivacyDetail.getGuardianPhoneNumber())
                .profileImg(oneseoPrivacyDetail.getProfileImg())
                .relationshipWithGuardian(oneseoPrivacyDetail.getRelationshipWithGuardian())
                .schoolAddress(oneseoPrivacyDetail.getSchoolAddress())
                .schoolName(oneseoPrivacyDetail.getSchoolName())
                .schoolTeacherName(oneseoPrivacyDetail.getSchoolTeacherName())
                .schoolTeacherPhoneNumber(oneseoPrivacyDetail.getSchoolTeacherPhoneNumber())
                .build();

        OneseoTestResultResDto oneseoTestResultResDto = OneseoTestResultResDto.builder()
                .aptitudeEvaluationScore(entranceTestResult.getAptitudeEvaluationScore())
                .artsPhysicalSubjectsScore(entranceTestFactorsDetail.getArtsPhysicalSubjectsScore())
                .interviewScore(entranceTestResult.getInterviewScore())
                .documentEvaluationScore(entranceTestResult.getDocumentEvaluationScore())
                .totalNonSubjectsScore(entranceTestFactorsDetail.getTotalNonSubjectsScore())
                .totalSubjectsScore(entranceTestFactorsDetail.getTotalSubjectsScore())
                .build();

        return FoundOneseoResDto.builder()
                .oneseoId(oneseo.getId())
                .desiredMajors(desiredMajorsResDto)
                .privacyDetail(oneseoPrivacyDetailResDto)
                .testResult(oneseoTestResultResDto)
                .submitCode(oneseo.getOneseoSubmitCode())
                .wantedScreening(oneseo.getWantedScreening())
                .build();
    }

    @Transactional(readOnly = true)
    public SearchOneseosResDto search(
            Integer page,
            Integer size,
            TestResultTag tag,
            ScreeningCategory screeningCategory,
            YesNo isSubmitted,
            String keyword
    ) {
        Pageable pageable = PageRequest.of(page, size);

        // ㅠㅠ
    }
}
