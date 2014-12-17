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
import io.corylee.mapgen.domain.Polygon;
import io.corylee.mapgen.repository.PolygonRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PolygonResource REST controller.
 *
 * @see PolygonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PolygonResourceTest {

    private static final BigDecimal DEFAULT_X = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_X = BigDecimal.ONE;
    
    private static final BigDecimal DEFAULT_Y = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_Y = BigDecimal.ONE;
    
    private static final Boolean DEFAULT_OCEAN = false;
    private static final Boolean UPDATED_OCEAN = true;
    private static final Boolean DEFAULT_WATER = false;
    private static final Boolean UPDATED_WATER = true;
    private static final Boolean DEFAULT_COAST = false;
    private static final Boolean UPDATED_COAST = true;
    private static final Integer DEFAULT_ELEVATION = 0;
    private static final Integer UPDATED_ELEVATION = 1;
    

    @Inject
    private PolygonRepository polygonRepository;

    private MockMvc restPolygonMockMvc;

    private Polygon polygon;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PolygonResource polygonResource = new PolygonResource();
        ReflectionTestUtils.setField(polygonResource, "polygonRepository", polygonRepository);
        this.restPolygonMockMvc = MockMvcBuilders.standaloneSetup(polygonResource).build();
    }

    @Before
    public void initTest() {
        polygon = new Polygon();
        polygon.setX(DEFAULT_X);
        polygon.setY(DEFAULT_Y);
        polygon.setOcean(DEFAULT_OCEAN);
        polygon.setWater(DEFAULT_WATER);
        polygon.setCoast(DEFAULT_COAST);
        polygon.setElevation(DEFAULT_ELEVATION);
    }

    @Test
    @Transactional
    public void createPolygon() throws Exception {
        // Validate the database is empty
        assertThat(polygonRepository.findAll()).hasSize(0);

        // Create the Polygon
        restPolygonMockMvc.perform(post("/app/rest/polygons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(polygon)))
                .andExpect(status().isOk());

        // Validate the Polygon in the database
        List<Polygon> polygons = polygonRepository.findAll();
        assertThat(polygons).hasSize(1);
        Polygon testPolygon = polygons.iterator().next();
        assertThat(testPolygon.getX()).isEqualTo(DEFAULT_X);
        assertThat(testPolygon.getY()).isEqualTo(DEFAULT_Y);
        assertThat(testPolygon.getOcean()).isEqualTo(DEFAULT_OCEAN);
        assertThat(testPolygon.getWater()).isEqualTo(DEFAULT_WATER);
        assertThat(testPolygon.getCoast()).isEqualTo(DEFAULT_COAST);
        assertThat(testPolygon.getElevation()).isEqualTo(DEFAULT_ELEVATION);
    }

    @Test
    @Transactional
    public void getAllPolygons() throws Exception {
        // Initialize the database
        polygonRepository.saveAndFlush(polygon);

        // Get all the polygons
        restPolygonMockMvc.perform(get("/app/rest/polygons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(polygon.getId().intValue()))
                .andExpect(jsonPath("$.[0].x").value(DEFAULT_X.intValue()))
                .andExpect(jsonPath("$.[0].y").value(DEFAULT_Y.intValue()))
                .andExpect(jsonPath("$.[0].ocean").value(DEFAULT_OCEAN.booleanValue()))
                .andExpect(jsonPath("$.[0].water").value(DEFAULT_WATER.booleanValue()))
                .andExpect(jsonPath("$.[0].coast").value(DEFAULT_COAST.booleanValue()))
                .andExpect(jsonPath("$.[0].elevation").value(DEFAULT_ELEVATION));
    }

    @Test
    @Transactional
    public void getPolygon() throws Exception {
        // Initialize the database
        polygonRepository.saveAndFlush(polygon);

        // Get the polygon
        restPolygonMockMvc.perform(get("/app/rest/polygons/{id}", polygon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(polygon.getId().intValue()))
            .andExpect(jsonPath("$.x").value(DEFAULT_X.intValue()))
            .andExpect(jsonPath("$.y").value(DEFAULT_Y.intValue()))
            .andExpect(jsonPath("$.ocean").value(DEFAULT_OCEAN.booleanValue()))
            .andExpect(jsonPath("$.water").value(DEFAULT_WATER.booleanValue()))
            .andExpect(jsonPath("$.coast").value(DEFAULT_COAST.booleanValue()))
            .andExpect(jsonPath("$.elevation").value(DEFAULT_ELEVATION));
    }

    @Test
    @Transactional
    public void getNonExistingPolygon() throws Exception {
        // Get the polygon
        restPolygonMockMvc.perform(get("/app/rest/polygons/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolygon() throws Exception {
        // Initialize the database
        polygonRepository.saveAndFlush(polygon);

        // Update the polygon
        polygon.setX(UPDATED_X);
        polygon.setY(UPDATED_Y);
        polygon.setOcean(UPDATED_OCEAN);
        polygon.setWater(UPDATED_WATER);
        polygon.setCoast(UPDATED_COAST);
        polygon.setElevation(UPDATED_ELEVATION);
        restPolygonMockMvc.perform(post("/app/rest/polygons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(polygon)))
                .andExpect(status().isOk());

        // Validate the Polygon in the database
        List<Polygon> polygons = polygonRepository.findAll();
        assertThat(polygons).hasSize(1);
        Polygon testPolygon = polygons.iterator().next();
        assertThat(testPolygon.getX()).isEqualTo(UPDATED_X);
        assertThat(testPolygon.getY()).isEqualTo(UPDATED_Y);
        assertThat(testPolygon.getOcean()).isEqualTo(UPDATED_OCEAN);
        assertThat(testPolygon.getWater()).isEqualTo(UPDATED_WATER);
        assertThat(testPolygon.getCoast()).isEqualTo(UPDATED_COAST);
        assertThat(testPolygon.getElevation()).isEqualTo(UPDATED_ELEVATION);
    }

    @Test
    @Transactional
    public void deletePolygon() throws Exception {
        // Initialize the database
        polygonRepository.saveAndFlush(polygon);

        // Get the polygon
        restPolygonMockMvc.perform(delete("/app/rest/polygons/{id}", polygon.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Polygon> polygons = polygonRepository.findAll();
        assertThat(polygons).hasSize(0);
    }
}
