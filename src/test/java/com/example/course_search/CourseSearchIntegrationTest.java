package com.example.course_search;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.course_search.config.TestElasticsearchContainer;
import com.example.course_search.loader.SampleCourseLoader;
import com.example.course_search.model.CourseDocument;
import com.example.course_search.repository.CourseRepository;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CourseSearchIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(CourseSearchIntegrationTest.class);

    @Container
    private static final TestElasticsearchContainer elasticsearchContainer = new TestElasticsearchContainer();

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private SampleCourseLoader sampleCourseLoader;

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.elasticsearch.uris", elasticsearchContainer::getHttpHostAddress);
    }

    @BeforeAll
    static void startContainer() {
        elasticsearchContainer.start();
    }

    @BeforeEach
    void setup() {
        if (template.indexOps(CourseDocument.class).exists()) {
            template.indexOps(CourseDocument.class).delete();
        }
        template.indexOps(CourseDocument.class).create();
        sampleCourseLoader.loadSampleData();
    }

    @AfterAll
    static void stopContainer() {
        elasticsearchContainer.stop();
    }

    @Test
    void shouldFindByCategory() {
        List<CourseDocument> results = courseRepository.findByCategory("Science");

        log.info("==> Results for findByCategory(\"Science\"):");
        results.forEach(course -> log.info(course.toString()));

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(course -> course.getCategory().equals("Science"));
    }

    @Test
    void shouldFindByMode() {
        List<CourseDocument> results = courseRepository.findByMode("Live");

        log.info("==> Results for findByMode(\"Live\"):");
        results.forEach(course -> log.info(course.toString()));

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(course -> course.getMode().equals("Live"));
    }

    @Test
    void shouldFindByNextSessionDateAfter() {
        Instant now = Instant.parse("2025-07-21T00:00:00Z");
        List<CourseDocument> results = courseRepository.findByNextSessionDateAfter(now);

        log.info("==> Results for findByNextSessionDateAfter(\"2025-07-21T00:00:00Z\"):");
        results.forEach(course -> log.info(course.toString()));

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(course -> course.getNextSessionDate().isAfter(now));
    }

    @Test
    void shouldFindByTitleOrDescription() {
        List<CourseDocument> results =
                courseRepository.findByTitleContainingOrDescriptionContaining("Math", "math");

        log.info("==> Results for findByTitleContainingOrDescriptionContaining(\"Math\", \"math\"):");
        results.forEach(course -> log.info(course.toString()));

        assertThat(results).isNotEmpty();
        assertThat(results).allMatch(course ->
                course.getTitle().toLowerCase().contains("math") ||
                course.getDescription().toLowerCase().contains("math"));
    }

    @Test
    void shouldLoadAllSampleCourses() {
        List<CourseDocument> results = StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                                            .collect(Collectors.toList());

        log.info("==> Results for findAll():");
        results.forEach(course -> log.info(course.toString()));

        assertThat(results).hasSize(3);
    }
}
