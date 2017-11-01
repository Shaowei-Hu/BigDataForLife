package com.shaowei.bigdatalife.service;

import com.shaowei.bigdatalife.domain.Simulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Simulation.
 */
public interface SimulationService {

    /**
     * Save a simulation.
     *
     * @param simulation the entity to save
     * @return the persisted entity
     */
    Simulation save(Simulation simulation);

    /**
     *  Get all the simulations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Simulation> findAll(Pageable pageable);

    /**
     *  Get the "id" simulation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Simulation findOne(Long id);

    /**
     *  Delete the "id" simulation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the simulation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Simulation> search(String query, Pageable pageable);
}
