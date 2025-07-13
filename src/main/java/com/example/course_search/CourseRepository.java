package com.example.course_search;

import java.time.Instant;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String> {

    List<CourseDocument> findByTitleContainingOrDescriptionContaining(String title, String description);
  
    List<CourseDocument> findByCategory(String category);

    List<CourseDocument> findByType(String type);

    List<CourseDocument> findByNextSessionDateAfter(Instant date);
}