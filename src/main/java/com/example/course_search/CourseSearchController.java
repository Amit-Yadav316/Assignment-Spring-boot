package com.example.course_search;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Course Search", description = "Endpoints for searching courses with filters and pagination")
@RestController
@RequestMapping("/api")
public class CourseSearchController {

    private final CourseSearchService courseSearchService;

    @Autowired
    public CourseSearchController(CourseSearchService courseSearchService) {
        this.courseSearchService = courseSearchService;
    }

    @Operation(
        summary = "Search for courses",
        description = "Filters courses by keyword, age, category, type, price range, start date, sorting, and pagination."
    )
    @GetMapping("/search")
    public Map<String, Object> searchCourses(
            @Parameter(description = "Search keyword (matches title or description)") 
            @RequestParam(required = false) String q,

            @Parameter(description = "Minimum age") 
            @RequestParam(required = false) Integer minAge,

            @Parameter(description = "Maximum age") 
            @RequestParam(required = false) Integer maxAge,

            @Parameter(description = "Minimum price") 
            @RequestParam(required = false) Double minPrice,

            @Parameter(description = "Maximum price") 
            @RequestParam(required = false) Double maxPrice,

            @Parameter(description = "Course category") 
            @RequestParam(required = false) String category,

            @Parameter(description = "Course mode (CLUB, COURSE, ONE_TIME)") 
            @RequestParam(required = false) String mode,

            @Parameter(description = "Start date in ISO format") 
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,

            @Parameter(description = "Sort by 'upcoming', 'priceAsc', or 'priceDesc'", example = "upcoming") 
            @RequestParam(defaultValue = "upcoming") String sort,

            @Parameter(description = "Page number (starting from 0)", example = "0") 
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of results per page", example = "10") 
            @RequestParam(defaultValue = "10") int size
    ) {
        CourseSearchService.SearchResult result = courseSearchService.searchCourses(
                q, minAge, maxAge, minPrice, maxPrice, category, mode, startDate, sort, page, size
        );

        return Map.of(
                "total", result.total(),
                "totalPages", result.totalPages(),
                "page", result.page(),
                "size", result.size(),
                "hasNext", result.hasNext(),
                "hasPrevious", result.hasPrevious(),
                "courses", result.courses()
        );
    }
}
