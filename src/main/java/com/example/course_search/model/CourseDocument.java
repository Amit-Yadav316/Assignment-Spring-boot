package com.example.course_search.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a course document stored in Elasticsearch")
@Document(indexName = "courses")
public class CourseDocument {

    @Id
    @Schema(description = "Unique course ID", example = "course_1")
    private String id;

    @Field(type = FieldType.Text)
    @Schema(description = "Course title", example = "Intro to Math Club")
    private String title;

    @Field(type = FieldType.Text)
    @Schema(description = "Course description", example = "This is a comprehensive course for students interested in math.")
    private String description;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Category of the course", example = "Math")
    private String category;
    
    // The 'mode' field is mapped to the JSON property 'type' to maintain compatibility with existing API contracts
    // where the property is named 'type', even though the internal field name is 'mode' for clarity.
        @JsonProperty("type")
        @Field(type = FieldType.Keyword)
        @Schema(description = "Type of course (CLUB, COURSE, ONE_TIME)", example = "CLUB")
        private String mode;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Grade range for which the course is suitable", example = "3rd–5th")
    private String gradeRange;

    @Field(type = FieldType.Integer)
    @Schema(description = "Minimum age requirement", example = "7")
    private int minAge;

    @Field(type = FieldType.Integer)
    @Schema(description = "Maximum age allowed", example = "11")
    private int maxAge;

    @Field(type = FieldType.Double)
    @Schema(description = "Price of the course", example = "99.99")
    private double price;

    @Field(type = FieldType.Date)
    @Schema(description = "Next session date in ISO format", example = "2025-08-20T15:48:17.156829Z")
    private Instant nextSessionDate;


    public CourseDocument() {}

    public CourseDocument(String id, String title, String description, String category, String mode, String gradeRange,
                          int minAge, int maxAge, double price, Instant nextSessionDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.mode = mode;
        this.gradeRange = gradeRange;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.price = price;
        this.nextSessionDate = nextSessionDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getGradeRange() { return gradeRange; }
    public void setGradeRange(String gradeRange) { this.gradeRange = gradeRange; }

    public int getMinAge() { return minAge; }
    public void setMinAge(int minAge) { this.minAge = minAge; }

    public int getMaxAge() { return maxAge; }
    public void setMaxAge(int maxAge) { this.maxAge = maxAge; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Instant getNextSessionDate() { return nextSessionDate; }
    public void setNextSessionDate(Instant nextSessionDate) { this.nextSessionDate = nextSessionDate; }

    @Override
    public String toString() {
        return "CourseDocument{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", mode='" + mode + '\'' +
                ", gradeRange='" + gradeRange + '\'' +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", price=" + price +
                ", nextSessionDate=" + nextSessionDate +
                '}';
    }
}