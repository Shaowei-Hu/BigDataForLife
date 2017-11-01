package com.shaowei.bigdatalife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shaowei.bigdatalife.domain.Visitor;
import com.shaowei.bigdatalife.service.VisitorService;
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

import javax.servlet.http.HttpServletRequest;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Visitor.
 */
@RestController
@RequestMapping("/api")
public class VisitorResource {

    private final Logger log = LoggerFactory.getLogger(VisitorResource.class);

    private static final String ENTITY_NAME = "visitor";

    private final VisitorService visitorService;

    public VisitorResource(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    /**
     * POST  /visitors : Create a new visitor.
     *
     * @param visitor the visitor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitor, or with status 400 (Bad Request) if the visitor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visitors")
    @Timed
    public ResponseEntity<Visitor> createVisitor(@RequestBody Visitor visitor) throws URISyntaxException {
        log.debug("REST request to save Visitor : {}", visitor);
        if (visitor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new visitor cannot already have an ID")).body(null);
        }
        Visitor result = visitorService.save(visitor);
        return ResponseEntity.created(new URI("/api/visitors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * POST  /visitors : Create a new visitor.
     *
     * @param visitor the visitor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitor, or with status 400 (Bad Request) if the visitor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visitor")
    @Timed
    public ResponseEntity<Visitor> createVisitor(HttpServletRequest requestContext) throws URISyntaxException {
        log.debug("REST request to create Visitor : {}");
        Visitor visitor = new Visitor();
        visitor.setIp(requestContext.getRemoteAddr());
        visitor.setBrowser(requestContext.getHeader("User-Agent"));
        Visitor result = visitorService.save(visitor);
        return ResponseEntity.created(new URI("/api/visitors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visitors : Updates an existing visitor.
     *
     * @param visitor the visitor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visitor,
     * or with status 400 (Bad Request) if the visitor is not valid,
     * or with status 500 (Internal Server Error) if the visitor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visitors")
    @Timed
    public ResponseEntity<Visitor> updateVisitor(@RequestBody Visitor visitor) throws URISyntaxException {
        log.debug("REST request to update Visitor : {}", visitor);
        if (visitor.getId() == null) {
            return createVisitor(visitor);
        }
        Visitor result = visitorService.save(visitor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visitors : get all the visitors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of visitors in body
     */
    @GetMapping("/visitors")
    @Timed
    public ResponseEntity<List<Visitor>> getAllVisitors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Visitors");
        Page<Visitor> page = visitorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/visitors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /visitors/:id : get the "id" visitor.
     *
     * @param id the id of the visitor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitor, or with status 404 (Not Found)
     */
    @GetMapping("/visitors/{id}")
    @Timed
    public ResponseEntity<Visitor> getVisitor(@PathVariable Long id) {
        log.debug("REST request to get Visitor : {}", id);
        Visitor visitor = visitorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visitor));
    }

    /**
     * DELETE  /visitors/:id : delete the "id" visitor.
     *
     * @param id the id of the visitor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visitors/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisitor(@PathVariable Long id) {
        log.debug("REST request to delete Visitor : {}", id);
        visitorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/visitors?query=:query : search for the visitor corresponding
     * to the query.
     *
     * @param query the query of the visitor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/visitors")
    @Timed
    public ResponseEntity<List<Visitor>> searchVisitors(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Visitors for query {}", query);
        Page<Visitor> page = visitorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/visitors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
