package team.themoment.hellogsmassignment.domain.oneseo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmassignment.domain.oneseo.dto.FoundOneseoResDto;
import team.themoment.hellogsmassignment.domain.oneseo.dto.SearchOneseosResDto;
import team.themoment.hellogsmassignment.domain.oneseo.dto.TestResultTag;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.ScreeningCategory;
import team.themoment.hellogsmassignment.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmassignment.domain.oneseo.service.OneseoService;

@RestController
@RequestMapping("/oneseo/v1")
@RequiredArgsConstructor
public class OneseoController {

    private final OneseoService oneseoService;

    @GetMapping("/{oneseo_id}")
    public ResponseEntity<FoundOneseoResDto> query(
            @PathVariable("oneseo_id") Long oneseoId
    ) {
        FoundOneseoResDto response = oneseoService.query(oneseoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchOneseosResDto> search(
          @RequestParam("page") Integer page,
          @RequestParam("size") Integer size,
          @RequestParam(name = "testResultTag") String testResultParam,
          @RequestParam(name = "screeningTag", required = false) String screeningParam,
          @RequestParam(name = "isSubmitted", required = false) String isSubmittedParam,
          @RequestParam(name = "keyword", required = false) String keyword
    ) {
        if (page < 0 || size < 0) {
            throw new RuntimeException("page, size는 0 이상만 가능합니다");
        }

        try {
            TestResultTag tag = TestResultTag.valueOf(testResultParam);
            ScreeningCategory screeningCategory = (screeningParam != null) ? ScreeningCategory.valueOf(screeningParam) : null;
            YesNo isSubmitted = (isSubmittedParam != null) ? YesNo.valueOf(isSubmittedParam) : null;

            SearchOneseosResDto response = oneseoService.search(page, size, tag, screeningCategory, isSubmitted, keyword);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 매개변수 값입니다.");
        }
    }

}
