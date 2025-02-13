package team.themoment.hellogsmassignment.domain.oneseo.entity.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesiredMajors {

    @Enumerated(EnumType.STRING)
    @Column(name = "first_desired_major", nullable = false)
    @NotNull
    private Major firstDesiredMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_desired_major", nullable = false)
    @NotNull
    private Major secondDesiredMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "third_desired_major", nullable = false)
    @NotNull
    private Major thirdDesiredMajor;
}
