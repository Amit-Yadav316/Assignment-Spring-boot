package com.example.course_search;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {

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
                System.out.println("Indexed " + courses.size() + " courses into Elasticsearch.");
            }
        } else {
            System.out.println("Elasticsearch already contains data. Skipping load.");
        }
    }

}