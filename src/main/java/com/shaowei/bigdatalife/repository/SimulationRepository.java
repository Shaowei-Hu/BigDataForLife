package com.shaowei.bigdatalife.repository;

import com.shaowei.bigdatalife.domain.Simulation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Simulation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {

}
