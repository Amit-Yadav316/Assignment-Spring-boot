package com.example.course_search.config;

import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

public class TestElasticsearchContainer extends ElasticsearchContainer {
    private static final DockerImageName IMAGE = DockerImageName
            .parse("elasticsearch:8.13.4")
            .asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch");

    public TestElasticsearchContainer() {
        super(IMAGE);
        addExposedPort(9200);
        addEnv("discovery.type", "single-node");
        addEnv("xpack.security.enabled", "false");
        addEnv("cluster.name", "elasticsearch");
    }
}