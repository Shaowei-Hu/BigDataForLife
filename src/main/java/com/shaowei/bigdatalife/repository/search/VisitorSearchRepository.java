package com.shaowei.bigdatalife.repository.search;

import com.shaowei.bigdatalife.domain.Visitor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Visitor entity.
 */
public interface VisitorSearchRepository extends ElasticsearchRepository<Visitor, Long> {
}
