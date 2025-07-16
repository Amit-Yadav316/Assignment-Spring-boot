package com.example.course_search.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.course_search.model.CourseDocument;

public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String> {

    List<CourseDocument> findByTitleContainingOrDescriptionContaining(String title, String description);
  
    List<CourseDocument> findByCategory(String category);

    List<CourseDocument> findByMode(String mode);

    List<CourseDocument> findByNextSessionDateAfter(Instant date);
}