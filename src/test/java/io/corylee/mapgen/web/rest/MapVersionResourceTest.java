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
import java.math.BigDecimal;
import java.util.List;

import io.corylee.mapgen.Application;
import io.corylee.mapgen.domain.MapVersion;
import io.corylee.mapgen.repository.MapVersionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MapVersionResource REST controller.
 *
 * @see MapVersionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MapVersionResourceTest {

    private static final BigDecimal DEFAULT_MAX_Y = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_MAX_Y = BigDecimal.ONE;
    
    private static final BigDecimal DEFAULT_MAX_X = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_MAX_X = BigDecimal.ONE;
    
    private static final Integer DEFAULT_VERSION = 0;
    private static final Integer UPDATED_VERSION = 1;
    

    @Inject
    private MapVersionRepository mapVersionRepository;

    private MockMvc restMapVersionMockMvc;

    private MapVersion mapVersion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MapVersionResource mapVersionResource = new MapVersionResource();
        ReflectionTestUtils.setField(mapVersionResource, "mapVersionRepository", mapVersionRepository);
        this.restMapVersionMockMvc = MockMvcBuilders.standaloneSetup(mapVersionResource).build();
    }

    @Before
    public void initTest() {
        mapVersion = new MapVersion();
        mapVersion.setMaxY(DEFAULT_MAX_Y);
        mapVersion.setMaxX(DEFAULT_MAX_X);
        mapVersion.setVersion(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createMapVersion() throws Exception {
        // Validate the database is empty
        assertThat(mapVersionRepository.findAll()).hasSize(0);

        // Create the MapVersion
        restMapVersionMockMvc.perform(post("/app/rest/mapVersions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mapVersion)))
                .andExpect(status().isOk());

        // Validate the MapVersion in the database
        List<MapVersion> mapVersions = mapVersionRepository.findAll();
        assertThat(mapVersions).hasSize(1);
        MapVersion testMapVersion = mapVersions.iterator().next();
        assertThat(testMapVersion.getMaxY()).isEqualTo(DEFAULT_MAX_Y);
        assertThat(testMapVersion.getMaxX()).isEqualTo(DEFAULT_MAX_X);
        assertThat(testMapVersion.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void getAllMapVersions() throws Exception {
        // Initialize the database
        mapVersionRepository.saveAndFlush(mapVersion);

        // Get all the mapVersions
        restMapVersionMockMvc.perform(get("/app/rest/mapVersions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(mapVersion.getId().intValue()))
                .andExpect(jsonPath("$.[0].maxY").value(DEFAULT_MAX_Y.intValue()))
                .andExpect(jsonPath("$.[0].maxX").value(DEFAULT_MAX_X.intValue()))
                .andExpect(jsonPath("$.[0].version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    public void getMapVersion() throws Exception {
        // Initialize the database
        mapVersionRepository.saveAndFlush(mapVersion);

        // Get the mapVersion
        restMapVersionMockMvc.perform(get("/app/rest/mapVersions/{id}", mapVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mapVersion.getId().intValue()))
            .andExpect(jsonPath("$.maxY").value(DEFAULT_MAX_Y.intValue()))
            .andExpect(jsonPath("$.maxX").value(DEFAULT_MAX_X.intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    public void getNonExistingMapVersion() throws Exception {
        // Get the mapVersion
        restMapVersionMockMvc.perform(get("/app/rest/mapVersions/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMapVersion() throws Exception {
        // Initialize the database
        mapVersionRepository.saveAndFlush(mapVersion);

        // Update the mapVersion
        mapVersion.setMaxY(UPDATED_MAX_Y);
        mapVersion.setMaxX(UPDATED_MAX_X);
        mapVersion.setVersion(UPDATED_VERSION);
        restMapVersionMockMvc.perform(post("/app/rest/mapVersions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mapVersion)))
                .andExpect(status().isOk());

        // Validate the MapVersion in the database
        List<MapVersion> mapVersions = mapVersionRepository.findAll();
        assertThat(mapVersions).hasSize(1);
        MapVersion testMapVersion = mapVersions.iterator().next();
        assertThat(testMapVersion.getMaxY()).isEqualTo(UPDATED_MAX_Y);
        assertThat(testMapVersion.getMaxX()).isEqualTo(UPDATED_MAX_X);
        assertThat(testMapVersion.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void deleteMapVersion() throws Exception {
        // Initialize the database
        mapVersionRepository.saveAndFlush(mapVersion);

        // Get the mapVersion
        restMapVersionMockMvc.perform(delete("/app/rest/mapVersions/{id}", mapVersion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MapVersion> mapVersions = mapVersionRepository.findAll();
        assertThat(mapVersions).hasSize(0);
    }
}
