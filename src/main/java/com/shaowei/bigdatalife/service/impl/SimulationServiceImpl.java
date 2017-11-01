package com.shaowei.bigdatalife.service.impl;

import com.shaowei.bigdatalife.service.SimulationService;
import com.shaowei.bigdatalife.domain.Simulation;
import com.shaowei.bigdatalife.repository.SimulationRepository;
import com.shaowei.bigdatalife.repository.search.SimulationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Simulation.
 */
@Service
@Transactional
public class SimulationServiceImpl implements SimulationService{

    private final Logger log = LoggerFactory.getLogger(SimulationServiceImpl.class);

    private final SimulationRepository simulationRepository;

    private final SimulationSearchRepository simulationSearchRepository;

    public SimulationServiceImpl(SimulationRepository simulationRepository, SimulationSearchRepository simulationSearchRepository) {
        this.simulationRepository = simulationRepository;
        this.simulationSearchRepository = simulationSearchRepository;
    }

    /**
     * Save a simulation.
     *
     * @param simulation the entity to save
     * @return the persisted entity
     */
    @Override
    public Simulation save(Simulation simulation) {
        log.debug("Request to save Simulation : {}", simulation);
        Simulation result = simulationRepository.save(simulation);
        simulationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the simulations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Simulation> findAll(Pageable pageable) {
        log.debug("Request to get all Simulations");
        return simulationRepository.findAll(pageable);
    }

    /**
     *  Get one simulation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Simulation findOne(Long id) {
        log.debug("Request to get Simulation : {}", id);
        return simulationRepository.findOne(id);
    }

    /**
     *  Delete the  simulation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Simulation : {}", id);
        simulationRepository.delete(id);
        simulationSearchRepository.delete(id);
    }

    /**
     * Search for the simulation corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Simulation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Simulations for query {}", query);
        Page<Simulation> result = simulationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
