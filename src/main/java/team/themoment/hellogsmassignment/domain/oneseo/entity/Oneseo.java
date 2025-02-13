package team.themoment.hellogsmassignment.domain.oneseo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import team.themoment.hellogsmassignment.domain.member.entity.Member;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.YesNo;

@Getter
@Entity
@Table(name = "tb_oneseo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Oneseo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oneseo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "oneseo", cascade = CascadeType.ALL, orphanRemoval = true)
    private EntranceTestResult entranceTestResult;

    @OneToOne(mappedBy = "oneseo", cascade = CascadeType.ALL, orphanRemoval = true)
    private OneseoPrivacyDetail oneseoPrivacyDetail;

    @Column(name = "oneseo_submit_code", unique = true)
    private String oneseoSubmitCode;

    @Column(name = "examination_number", length = 4, unique = true)
    private String examinationNumber;

    @Column(name = "pass_yn")
    @Enumerated(EnumType.STRING)
    private YesNo passYn;

    @NotNull
    @Embedded
    protected DesiredMajors desiredMajors;

    @Enumerated(EnumType.STRING)
    @Column(name = "real_oneseo_arrived_yn", nullable = false)
    private YesNo realOneseoArrivedYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "wanted_screening", nullable = false)
    private Screening wantedScreening;

    @Enumerated(EnumType.STRING)
    @Column(name = "applied_screening")
    private Screening appliedScreening;

    @Enumerated(EnumType.STRING)
    @Column(name = "entrance_intention_yn")
    private YesNo entranceIntentionYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "decided_major")
    private Major decidedMajor;

}
