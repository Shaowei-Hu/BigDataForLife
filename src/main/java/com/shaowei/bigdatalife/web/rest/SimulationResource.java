package com.shaowei.bigdatalife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shaowei.bigdatalife.domain.Simulation;
import com.shaowei.bigdatalife.service.SimulationService;
import com.shaowei.bigdatalife.web.rest.util.HeaderUtil;
import com.shaowei.bigdatalife.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Simulation.
 */
@RestController
@RequestMapping("/api")
public class SimulationResource {

    private final Logger log = LoggerFactory.getLogger(SimulationResource.class);

    private static final String ENTITY_NAME = "simulation";

    private final SimulationService simulationService;

    public SimulationResource(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    /**
     * POST  /simulations : Create a new simulation.
     *
     * @param simulation the simulation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new simulation, or with status 400 (Bad Request) if the simulation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/simulations")
    @Timed
    public ResponseEntity<Simulation> createSimulation(@RequestBody Simulation simulation) throws URISyntaxException {
        log.debug("REST request to save Simulation : {}", simulation);
        if (simulation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new simulation cannot already have an ID")).body(null);
        }
        Simulation result = simulationService.save(simulation);
        return ResponseEntity.created(new URI("/api/simulations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /simulations : Updates an existing simulation.
     *
     * @param simulation the simulation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated simulation,
     * or with status 400 (Bad Request) if the simulation is not valid,
     * or with status 500 (Internal Server Error) if the simulation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/simulations")
    @Timed
    public ResponseEntity<Simulation> updateSimulation(@RequestBody Simulation simulation) throws URISyntaxException {
        log.debug("REST request to update Simulation : {}", simulation);
        if (simulation.getId() == null) {
            return createSimulation(simulation);
        }
        Simulation result = simulationService.save(simulation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, simulation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /simulations : get all the simulations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of simulations in body
     */
    @GetMapping("/simulations")
    @Timed
    public ResponseEntity<List<Simulation>> getAllSimulations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Simulations");
        Page<Simulation> page = simulationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/simulations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /simulations/:id : get the "id" simulation.
     *
     * @param id the id of the simulation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the simulation, or with status 404 (Not Found)
     */
    @GetMapping("/simulations/{id}")
    @Timed
    public ResponseEntity<Simulation> getSimulation(@PathVariable Long id) {
        log.debug("REST request to get Simulation : {}", id);
        Simulation simulation = simulationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(simulation));
    }

    /**
     * DELETE  /simulations/:id : delete the "id" simulation.
     *
     * @param id the id of the simulation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/simulations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSimulation(@PathVariable Long id) {
        log.debug("REST request to delete Simulation : {}", id);
        simulationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/simulations?query=:query : search for the simulation corresponding
     * to the query.
     *
     * @param query the query of the simulation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/simulations")
    @Timed
    public ResponseEntity<List<Simulation>> searchSimulations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Simulations for query {}", query);
        Page<Simulation> page = simulationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/simulations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
