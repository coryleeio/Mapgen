package io.corylee.mapgen.repository;

import io.corylee.mapgen.domain.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Polygon entity.
 */
public interface PolygonRepository extends JpaRepository<Polygon, Long> {

}
