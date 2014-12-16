package io.corylee.mapgen.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.corylee.mapgen.domain.MapVersion;
import io.corylee.mapgen.repository.MapVersionRepository;
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
 * REST controller for managing MapVersion.
 */
@RestController
@RequestMapping("/app")
public class MapVersionResource {

    private final Logger log = LoggerFactory.getLogger(MapVersionResource.class);

    @Inject
    private MapVersionRepository mapVersionRepository;

    /**
     * POST  /rest/mapVersions -> Create a new mapVersion.
     */
    @RequestMapping(value = "/rest/mapVersions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody MapVersion mapVersion) {
        log.debug("REST request to save MapVersion : {}", mapVersion);
        mapVersionRepository.save(mapVersion);
    }

    /**
     * GET  /rest/mapVersions -> get all the mapVersions.
     */
    @RequestMapping(value = "/rest/mapVersions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MapVersion> getAll() {
        log.debug("REST request to get all MapVersions");
        return mapVersionRepository.findAll();
    }

    /**
     * GET  /rest/mapVersions/:id -> get the "id" mapVersion.
     */
    @RequestMapping(value = "/rest/mapVersions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MapVersion> get(@PathVariable Long id) {
        log.debug("REST request to get MapVersion : {}", id);
        return Optional.ofNullable(mapVersionRepository.findOne(id))
            .map(mapVersion -> new ResponseEntity<>(
                mapVersion,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/mapVersions/:id -> delete the "id" mapVersion.
     */
    @RequestMapping(value = "/rest/mapVersions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete MapVersion : {}", id);
        mapVersionRepository.delete(id);
    }
}
