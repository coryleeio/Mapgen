package io.corylee.mapgen.web.rest;

import io.corylee.mapgen.domain.Map;
import io.corylee.mapgen.repository.CornerRepository;
import io.corylee.mapgen.repository.MapRepository;
import io.corylee.mapgen.repository.MapVersionRepository;
import io.corylee.mapgen.repository.PolygonRepository;
import io.corylee.mapgen.web.rest.dto.MapDTO;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Map.
 */
@RestController
@RequestMapping("/app")
public class MapResource {

    private final Logger log = LoggerFactory.getLogger(MapResource.class);

    @Inject
    private MapRepository mapRepository;
    
    @Inject
    private MapVersionRepository mapVersionRepository;
    
    @Inject
    private PolygonRepository nodeRepository;
    
    @Inject
    private CornerRepository cornerRepository;
    
    @Inject
    private CornerRepository edgeRepository;

    /**
     * POST  /rest/maps -> Create a new map.
     */
    @RequestMapping(value = "/rest/maps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Map map) {
        log.debug("REST request to save Map : {}", map);
        mapRepository.save(map);
    }

    /**
     * GET  /rest/maps -> get all the maps.
     */
    @RequestMapping(value = "/rest/maps",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map> getAll() {
        log.debug("REST request to get all Maps");
        return mapRepository.findAll();
    }

    /**
     * GET  /rest/maps/:id -> get the "id" map.
     */
    @RequestMapping(value = "/rest/maps/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MapDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Map : {}", id);
        Map map = mapRepository.findOne(id);
        final MapDTO dto = new MapDTO(map, map.getMapVersion(), null, null, null);
        return Optional.ofNullable(dto)
            .map(myDto -> new ResponseEntity<>(
            	dto,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/maps/:id -> delete the "id" map.
     */
    @RequestMapping(value = "/rest/maps/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Map : {}", id);
        mapRepository.delete(id);
    }
}
