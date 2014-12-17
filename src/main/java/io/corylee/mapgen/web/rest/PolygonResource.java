package io.corylee.mapgen.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.corylee.mapgen.domain.Polygon;
import io.corylee.mapgen.repository.PolygonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Polygon.
 */
@RestController
@RequestMapping("/app")
public class PolygonResource {

    private final Logger log = LoggerFactory.getLogger(PolygonResource.class);

    @Inject
    private PolygonRepository polygonRepository;

    /**
     * POST  /rest/polygons -> Create a new polygon.
     */
    @RequestMapping(value = "/rest/polygons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Polygon polygon) {
        log.debug("REST request to save Polygon : {}", polygon);
        polygonRepository.save(polygon);
    }

    /**
     * GET  /rest/polygons -> get all the polygons.
     */
    @RequestMapping(value = "/rest/polygons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Polygon> getAll() {
        log.debug("REST request to get all Polygons");
        return polygonRepository.findAll();
    }

    /**
     * GET  /rest/polygons/:id -> get the "id" polygon.
     */
    @RequestMapping(value = "/rest/polygons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Polygon> get(@PathVariable Long id) {
        log.debug("REST request to get Polygon : {}", id);
        return Optional.ofNullable(polygonRepository.findOne(id))
            .map(polygon -> new ResponseEntity<>(
                polygon,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/polygons/:id -> delete the "id" polygon.
     */
    @RequestMapping(value = "/rest/polygons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Polygon : {}", id);
        polygonRepository.delete(id);
    }
}
