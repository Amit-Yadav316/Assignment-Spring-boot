package com.example.course_search.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import com.example.course_search.model.CourseDocument;

@Service
public class CourseSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public CourseSearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public SearchResult searchCourses(String q,
                                      Integer minAge,
                                      Integer maxAge,
                                      Double minPrice,
                                      Double maxPrice,
                                      String category,
                                      String mode,
                                      Instant startDate,
                                      String sort,
                                      int page,
                                      int size) {

        List<Criteria> criteriaList = new ArrayList<>();

        if (q != null && !q.isBlank()) {
            Criteria titleCriteria = new Criteria("title").matches(q);
            Criteria descCriteria = new Criteria("description").matches(q);
            criteriaList.add(titleCriteria.or(descCriteria));
        }

        if (minAge != null && maxAge != null) {
            criteriaList.add(new Criteria("minAge").lessThanEqual(maxAge)
                              .and("maxAge").greaterThanEqual(minAge));
        } else if (minAge != null) {
            criteriaList.add(new Criteria("maxAge").greaterThanEqual(minAge));
        } else if (maxAge != null) {
            criteriaList.add(new Criteria("minAge").lessThanEqual(maxAge));
        }

        if (minPrice != null || maxPrice != null) {
            Criteria priceCriteria = new Criteria("price");
            if (minPrice != null) {
                priceCriteria = priceCriteria.greaterThanEqual(minPrice);
            }
            if (maxPrice != null) {
                priceCriteria = priceCriteria.and(new Criteria("price").lessThanEqual(maxPrice));
            }
            criteriaList.add(priceCriteria);
        }

        if (category != null && !category.isBlank()) {
            criteriaList.add(new Criteria("category").is(category));
        }

        if (mode != null && !mode.isBlank()) {
            criteriaList.add(new Criteria("mode").is(mode));
        }

        if (startDate != null) {
            criteriaList.add(new Criteria("nextSessionDate").greaterThanEqual(startDate.toString()));
        }

        Criteria finalCriteria = new Criteria();
        for (Criteria c : criteriaList) {
            finalCriteria = finalCriteria.and(c);
        }

        Pageable pageable = PageRequest.of(page, size);

        CriteriaQuery query = new CriteriaQuery(finalCriteria, pageable);
        
        Sort sortObj;
        if ("priceAsc".equals(sort)) {
            sortObj = Sort.by(Sort.Order.asc("price"));
        } else if ("priceDesc".equals(sort)) {
            sortObj = Sort.by(Sort.Order.desc("price"));
        } else {
            sortObj = Sort.by(Sort.Order.asc("nextSessionDate"));
        }
        query.addSort(sortObj);

        SearchHits<CourseDocument> hits = elasticsearchOperations.search(query, CourseDocument.class);
        List<CourseDocument> courses = hits.stream().map(SearchHit::getContent).toList();
        long totalHits = hits.getTotalHits();

        return new SearchResult(courses, totalHits, page, size);
    }

    public record SearchResult(List<CourseDocument> courses, long total, int page, int size) {
        public boolean hasNext() {
            return (page + 1) * size < total;
        }

        public boolean hasPrevious() {
            return page > 0;
        }

        public long totalPages() {
            return (long) Math.ceil((double) total / size);
        }
    }
}
