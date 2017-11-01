package com.shaowei.bigdatalife.service;

import com.shaowei.bigdatalife.domain.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Visitor.
 */
public interface VisitorService {

    /**
     * Save a visitor.
     *
     * @param visitor the entity to save
     * @return the persisted entity
     */
    Visitor save(Visitor visitor);

    /**
     *  Get all the visitors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Visitor> findAll(Pageable pageable);

    /**
     *  Get the "id" visitor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Visitor findOne(Long id);

    /**
     *  Delete the "id" visitor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the visitor corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Visitor> search(String query, Pageable pageable);
}
