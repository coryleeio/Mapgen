package io.corylee.mapgen.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import io.corylee.mapgen.Application;
import io.corylee.mapgen.domain.Map;
import io.corylee.mapgen.repository.MapRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MapResource REST controller.
 *
 * @see MapResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MapResourceTest {


    @Inject
    private MapRepository mapRepository;

    private MockMvc restMapMockMvc;

    private Map map;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MapResource mapResource = new MapResource();
        ReflectionTestUtils.setField(mapResource, "mapRepository", mapRepository);
        this.restMapMockMvc = MockMvcBuilders.standaloneSetup(mapResource).build();
    }

    @Before
    public void initTest() {
        map = new Map();
    }

    @Test
    @Transactional
    public void createMap() throws Exception {
        // Validate the database is empty
        assertThat(mapRepository.findAll()).hasSize(0);

        // Create the Map
        restMapMockMvc.perform(post("/app/rest/maps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isOk());

        // Validate the Map in the database
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(1);
        Map testMap = maps.iterator().next();
    }

    @Test
    @Transactional
    public void getAllMaps() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get all the maps
        restMapMockMvc.perform(get("/app/rest/maps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(map.getId().intValue()));
    }

    @Test
    @Transactional
    public void getMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get the map
        restMapMockMvc.perform(get("/app/rest/maps/{id}", map.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(map.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMap() throws Exception {
        // Get the map
        restMapMockMvc.perform(get("/app/rest/maps/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Update the map
        restMapMockMvc.perform(post("/app/rest/maps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isOk());

        // Validate the Map in the database
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(1);
        Map testMap = maps.iterator().next();
    }

    @Test
    @Transactional
    public void deleteMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get the map
        restMapMockMvc.perform(delete("/app/rest/maps/{id}", map.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(0);
    }
}
