package com.shaowei.bigdatalife.web.rest;

import com.shaowei.bigdatalife.BigDataForLifeApp;

import com.shaowei.bigdatalife.domain.Visitor;
import com.shaowei.bigdatalife.repository.VisitorRepository;
import com.shaowei.bigdatalife.service.VisitorService;
import com.shaowei.bigdatalife.repository.search.VisitorSearchRepository;
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
 * Test class for the VisitorResource REST controller.
 *
 * @see VisitorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BigDataForLifeApp.class)
public class VisitorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_BROWSER = "AAAAAAAAAA";
    private static final String UPDATED_BROWSER = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ARRIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ARRIVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LEAVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LEAVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private VisitorSearchRepository visitorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitorMockMvc;

    private Visitor visitor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitorResource visitorResource = new VisitorResource(visitorService);
        this.restVisitorMockMvc = MockMvcBuilders.standaloneSetup(visitorResource)
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
    public static Visitor createEntity(EntityManager em) {
        Visitor visitor = new Visitor()
            .name(DEFAULT_NAME)
            .ip(DEFAULT_IP)
            .browser(DEFAULT_BROWSER)
            .information(DEFAULT_INFORMATION)
            .arriveDate(DEFAULT_ARRIVE_DATE)
            .leaveDate(DEFAULT_LEAVE_DATE);
        return visitor;
    }

    @Before
    public void initTest() {
        visitorSearchRepository.deleteAll();
        visitor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisitor() throws Exception {
        int databaseSizeBeforeCreate = visitorRepository.findAll().size();

        // Create the Visitor
        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isCreated());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeCreate + 1);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVisitor.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testVisitor.getBrowser()).isEqualTo(DEFAULT_BROWSER);
        assertThat(testVisitor.getInformation()).isEqualTo(DEFAULT_INFORMATION);
        assertThat(testVisitor.getArriveDate()).isEqualTo(DEFAULT_ARRIVE_DATE);
        assertThat(testVisitor.getLeaveDate()).isEqualTo(DEFAULT_LEAVE_DATE);

        // Validate the Visitor in Elasticsearch
        Visitor visitorEs = visitorSearchRepository.findOne(testVisitor.getId());
        assertThat(visitorEs).isEqualToComparingFieldByField(testVisitor);
    }

    @Test
    @Transactional
    public void createVisitorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitorRepository.findAll().size();

        // Create the Visitor with an existing ID
        visitor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVisitors() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        // Get all the visitorList
        restVisitorMockMvc.perform(get("/api/visitors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].browser").value(hasItem(DEFAULT_BROWSER.toString())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].arriveDate").value(hasItem(sameInstant(DEFAULT_ARRIVE_DATE))))
            .andExpect(jsonPath("$.[*].leaveDate").value(hasItem(sameInstant(DEFAULT_LEAVE_DATE))));
    }

    @Test
    @Transactional
    public void getVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        // Get the visitor
        restVisitorMockMvc.perform(get("/api/visitors/{id}", visitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visitor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.browser").value(DEFAULT_BROWSER.toString()))
            .andExpect(jsonPath("$.information").value(DEFAULT_INFORMATION.toString()))
            .andExpect(jsonPath("$.arriveDate").value(sameInstant(DEFAULT_ARRIVE_DATE)))
            .andExpect(jsonPath("$.leaveDate").value(sameInstant(DEFAULT_LEAVE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingVisitor() throws Exception {
        // Get the visitor
        restVisitorMockMvc.perform(get("/api/visitors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisitor() throws Exception {
        // Initialize the database
        visitorService.save(visitor);

        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // Update the visitor
        Visitor updatedVisitor = visitorRepository.findOne(visitor.getId());
        updatedVisitor
            .name(UPDATED_NAME)
            .ip(UPDATED_IP)
            .browser(UPDATED_BROWSER)
            .information(UPDATED_INFORMATION)
            .arriveDate(UPDATED_ARRIVE_DATE)
            .leaveDate(UPDATED_LEAVE_DATE);

        restVisitorMockMvc.perform(put("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitor)))
            .andExpect(status().isOk());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVisitor.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testVisitor.getBrowser()).isEqualTo(UPDATED_BROWSER);
        assertThat(testVisitor.getInformation()).isEqualTo(UPDATED_INFORMATION);
        assertThat(testVisitor.getArriveDate()).isEqualTo(UPDATED_ARRIVE_DATE);
        assertThat(testVisitor.getLeaveDate()).isEqualTo(UPDATED_LEAVE_DATE);

        // Validate the Visitor in Elasticsearch
        Visitor visitorEs = visitorSearchRepository.findOne(testVisitor.getId());
        assertThat(visitorEs).isEqualToComparingFieldByField(testVisitor);
    }

    @Test
    @Transactional
    public void updateNonExistingVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // Create the Visitor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisitorMockMvc.perform(put("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isCreated());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisitor() throws Exception {
        // Initialize the database
        visitorService.save(visitor);

        int databaseSizeBeforeDelete = visitorRepository.findAll().size();

        // Get the visitor
        restVisitorMockMvc.perform(delete("/api/visitors/{id}", visitor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean visitorExistsInEs = visitorSearchRepository.exists(visitor.getId());
        assertThat(visitorExistsInEs).isFalse();

        // Validate the database is empty
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVisitor() throws Exception {
        // Initialize the database
        visitorService.save(visitor);

        // Search the visitor
        restVisitorMockMvc.perform(get("/api/_search/visitors?query=id:" + visitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].browser").value(hasItem(DEFAULT_BROWSER.toString())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].arriveDate").value(hasItem(sameInstant(DEFAULT_ARRIVE_DATE))))
            .andExpect(jsonPath("$.[*].leaveDate").value(hasItem(sameInstant(DEFAULT_LEAVE_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visitor.class);
        Visitor visitor1 = new Visitor();
        visitor1.setId(1L);
        Visitor visitor2 = new Visitor();
        visitor2.setId(visitor1.getId());
        assertThat(visitor1).isEqualTo(visitor2);
        visitor2.setId(2L);
        assertThat(visitor1).isNotEqualTo(visitor2);
        visitor1.setId(null);
        assertThat(visitor1).isNotEqualTo(visitor2);
    }
}
