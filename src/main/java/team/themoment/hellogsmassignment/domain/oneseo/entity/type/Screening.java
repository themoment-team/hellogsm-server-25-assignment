package team.themoment.hellogsmassignment.domain.oneseo.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Screening {
    GENERAL(ScreeningCategory.GENERAL),
    SPECIAL(ScreeningCategory.SPECIAL),
    EXTRA_VETERANS(ScreeningCategory.EXTRA),
    EXTRA_ADMISSION(ScreeningCategory.EXTRA);

    private final ScreeningCategory screeningCategory;
}
