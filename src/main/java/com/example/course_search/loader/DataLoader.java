package com.example.course_search.loader;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.course_search.model.CourseDocument;
import com.example.course_search.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:sample-courses.json")
    private Resource jsonFile;

    public DataLoader(CourseRepository courseRepository, ObjectMapper objectMapper) {
        this.courseRepository = courseRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (courseRepository.count() == 0) {
            try (InputStream inputStream = jsonFile.getInputStream()) {
                List<CourseDocument> courses = objectMapper.readValue(inputStream, new TypeReference<>() {});
                courseRepository.saveAll(courses);
                logger.info("Indexed {} courses into Elasticsearch.", courses.size());
            }
        } else {
            logger.info("Elasticsearch already contains data. Skipping load.");
        }
    }

}