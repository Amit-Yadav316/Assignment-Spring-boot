package com.example.course_search.loader;

import java.time.Instant;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.course_search.model.CourseDocument;
import com.example.course_search.repository.CourseRepository;

@Component
@Profile("test")
public class SampleCourseLoader {

    private final CourseRepository repository;

    public SampleCourseLoader(CourseRepository repository) {
        this.repository = repository;
    }

    public void loadSampleData() {
        repository.deleteAll();

        List<CourseDocument> sampleCourses = List.of(
                new CourseDocument("1", "Physics Basics", "Intro physics", "Science", "Self-paced", "6-8", 10, 14, 300, Instant.parse("2025-08-01T00:00:00Z")),
                new CourseDocument("2", "Advanced Physics", "Hardcore physics", "Science", "Live", "9-12", 15, 18, 500, Instant.parse("2025-09-15T00:00:00Z")),
                new CourseDocument("3", "Math for Kids", "Basic math", "Mathematics", "Live", "3-5", 6, 9, 200, Instant.parse("2025-07-20T00:00:00Z"))
        );

        repository.saveAll(sampleCourses);
    }
}
