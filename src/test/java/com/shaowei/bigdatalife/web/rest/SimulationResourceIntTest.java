package com.shaowei.bigdatalife.web.rest;

import com.shaowei.bigdatalife.BigDataForLifeApp;

import com.shaowei.bigdatalife.domain.Simulation;
import com.shaowei.bigdatalife.repository.SimulationRepository;
import com.shaowei.bigdatalife.service.SimulationService;
import com.shaowei.bigdatalife.repository.search.SimulationSearchRepository;
import com.shaowei.bigdatalife.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.shaowei.bigdatalife.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SimulationResource REST controller.
 *
 * @see SimulationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BigDataForLifeApp.class)
public class SimulationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_TAX_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_INTENTION = "AAAAAAAAAA";
    private static final String UPDATED_INTENTION = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private SimulationService simulationService;

    @Autowired
    private SimulationSearchRepository simulationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSimulationMockMvc;

    private Simulation simulation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SimulationResource simulationResource = new SimulationResource(simulationService);
        this.restSimulationMockMvc = MockMvcBuilders.standaloneSetup(simulationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Simulation createEntity(EntityManager em) {
        Simulation simulation = new Simulation()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .taxLevel(DEFAULT_TAX_LEVEL)
            .condition(DEFAULT_CONDITION)
            .intention(DEFAULT_INTENTION)
            .ip(DEFAULT_IP)
            .information(DEFAULT_INFORMATION)
            .date(DEFAULT_DATE);
        return simulation;
    }

    @Before
    public void initTest() {
        simulationSearchRepository.deleteAll();
        simulation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSimulation() throws Exception {
        int databaseSizeBeforeCreate = simulationRepository.findAll().size();

        // Create the Simulation
        restSimulationMockMvc.perform(post("/api/simulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(simulation)))
            .andExpect(status().isCreated());

        // Validate the Simulation in the database
        List<Simulation> simulationList = simulationRepository.findAll();
        assertThat(simulationList).hasSize(databaseSizeBeforeCreate + 1);
        Simulation testSimulation = simulationList.get(simulationList.size() - 1);
        assertThat(testSimulation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSimulation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSimulation.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testSimulation.getTaxLevel()).isEqualTo(DEFAULT_TAX_LEVEL);
        assertThat(testSimulation.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testSimulation.getIntention()).isEqualTo(DEFAULT_INTENTION);
        assertThat(testSimulation.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testSimulation.getInformation()).isEqualTo(DEFAULT_INFORMATION);
        assertThat(testSimulation.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the Simulation in Elasticsearch
        Simulation simulationEs = simulationSearchRepository.findOne(testSimulation.getId());
        assertThat(simulationEs).isEqualToComparingFieldByField(testSimulation);
    }

    @Test
    @Transactional
    public void createSimulationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = simulationRepository.findAll().size();

        // Create the Simulation with an existing ID
        simulation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSimulationMockMvc.perform(post("/api/simulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(simulation)))
            .andExpect(status().isBadRequest());

        // Validate the Simulation in the database
        List<Simulation> simulationList = simulationRepository.findAll();
        assertThat(simulationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSimulations() throws Exception {
        // Initialize the database
        simulationRepository.saveAndFlush(simulation);

        // Get all the simulationList
        restSimulationMockMvc.perform(get("/api/simulations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(simulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].taxLevel").value(hasItem(DEFAULT_TAX_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].intention").value(hasItem(DEFAULT_INTENTION.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getSimulation() throws Exception {
        // Initialize the database
        simulationRepository.saveAndFlush(simulation);

        // Get the simulation
        restSimulationMockMvc.perform(get("/api/simulations/{id}", simulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(simulation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.taxLevel").value(DEFAULT_TAX_LEVEL.toString()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()))
            .andExpect(jsonPath("$.intention").value(DEFAULT_INTENTION.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.information").value(DEFAULT_INFORMATION.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSimulation() throws Exception {
        // Get the simulation
        restSimulationMockMvc.perform(get("/api/simulations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSimulation() throws Exception {
        // Initialize the database
        simulationService.save(simulation);

        int databaseSizeBeforeUpdate = simulationRepository.findAll().size();

        // Update the simulation
        Simulation updatedSimulation = simulationRepository.findOne(simulation.getId());
        updatedSimulation
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .taxLevel(UPDATED_TAX_LEVEL)
            .condition(UPDATED_CONDITION)
            .intention(UPDATED_INTENTION)
            .ip(UPDATED_IP)
            .information(UPDATED_INFORMATION)
            .date(UPDATED_DATE);

        restSimulationMockMvc.perform(put("/api/simulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSimulation)))
            .andExpect(status().isOk());

        // Validate the Simulation in the database
        List<Simulation> simulationList = simulationRepository.findAll();
        assertThat(simulationList).hasSize(databaseSizeBeforeUpdate);
        Simulation testSimulation = simulationList.get(simulationList.size() - 1);
        assertThat(testSimulation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSimulation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSimulation.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testSimulation.getTaxLevel()).isEqualTo(UPDATED_TAX_LEVEL);
        assertThat(testSimulation.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testSimulation.getIntention()).isEqualTo(UPDATED_INTENTION);
        assertThat(testSimulation.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testSimulation.getInformation()).isEqualTo(UPDATED_INFORMATION);
        assertThat(testSimulation.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the Simulation in Elasticsearch
        Simulation simulationEs = simulationSearchRepository.findOne(testSimulation.getId());
        assertThat(simulationEs).isEqualToComparingFieldByField(testSimulation);
    }

    @Test
    @Transactional
    public void updateNonExistingSimulation() throws Exception {
        int databaseSizeBeforeUpdate = simulationRepository.findAll().size();

        // Create the Simulation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSimulationMockMvc.perform(put("/api/simulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(simulation)))
            .andExpect(status().isCreated());

        // Validate the Simulation in the database
        List<Simulation> simulationList = simulationRepository.findAll();
        assertThat(simulationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSimulation() throws Exception {
        // Initialize the database
        simulationService.save(simulation);

        int databaseSizeBeforeDelete = simulationRepository.findAll().size();

        // Get the simulation
        restSimulationMockMvc.perform(delete("/api/simulations/{id}", simulation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean simulationExistsInEs = simulationSearchRepository.exists(simulation.getId());
        assertThat(simulationExistsInEs).isFalse();

        // Validate the database is empty
        List<Simulation> simulationList = simulationRepository.findAll();
        assertThat(simulationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSimulation() throws Exception {
        // Initialize the database
        simulationService.save(simulation);

        // Search the simulation
        restSimulationMockMvc.perform(get("/api/_search/simulations?query=id:" + simulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(simulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].taxLevel").value(hasItem(DEFAULT_TAX_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].intention").value(hasItem(DEFAULT_INTENTION.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Simulation.class);
        Simulation simulation1 = new Simulation();
        simulation1.setId(1L);
        Simulation simulation2 = new Simulation();
        simulation2.setId(simulation1.getId());
        assertThat(simulation1).isEqualTo(simulation2);
        simulation2.setId(2L);
        assertThat(simulation1).isNotEqualTo(simulation2);
        simulation1.setId(null);
        assertThat(simulation1).isNotEqualTo(simulation2);
    }
}
