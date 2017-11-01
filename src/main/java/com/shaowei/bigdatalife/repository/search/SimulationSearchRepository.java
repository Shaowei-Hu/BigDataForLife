package com.shaowei.bigdatalife.repository.search;

import com.shaowei.bigdatalife.domain.Simulation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Simulation entity.
 */
public interface SimulationSearchRepository extends ElasticsearchRepository<Simulation, Long> {
}
