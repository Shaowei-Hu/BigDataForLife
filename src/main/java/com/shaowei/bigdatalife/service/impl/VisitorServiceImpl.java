package com.shaowei.bigdatalife.service.impl;

import com.shaowei.bigdatalife.service.VisitorService;
import com.shaowei.bigdatalife.domain.Visitor;
import com.shaowei.bigdatalife.repository.VisitorRepository;
import com.shaowei.bigdatalife.repository.search.VisitorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Visitor.
 */
@Service
@Transactional
public class VisitorServiceImpl implements VisitorService{

    private final Logger log = LoggerFactory.getLogger(VisitorServiceImpl.class);

    private final VisitorRepository visitorRepository;

    private final VisitorSearchRepository visitorSearchRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository, VisitorSearchRepository visitorSearchRepository) {
        this.visitorRepository = visitorRepository;
        this.visitorSearchRepository = visitorSearchRepository;
    }

    /**
     * Save a visitor.
     *
     * @param visitor the entity to save
     * @return the persisted entity
     */
    @Override
    public Visitor save(Visitor visitor) {
        log.debug("Request to save Visitor : {}", visitor);
        Visitor result = visitorRepository.save(visitor);
        visitorSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the visitors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Visitor> findAll(Pageable pageable) {
        log.debug("Request to get all Visitors");
        return visitorRepository.findAll(pageable);
    }

    /**
     *  Get one visitor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Visitor findOne(Long id) {
        log.debug("Request to get Visitor : {}", id);
        return visitorRepository.findOne(id);
    }

    /**
     *  Delete the  visitor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Visitor : {}", id);
        visitorRepository.delete(id);
        visitorSearchRepository.delete(id);
    }

    /**
     * Search for the visitor corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Visitor> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Visitors for query {}", query);
        Page<Visitor> result = visitorSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
