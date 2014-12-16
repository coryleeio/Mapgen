package io.corylee.mapgen.repository;

import io.corylee.mapgen.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Map entity.
 */
public interface MapRepository extends JpaRepository<Map, Long> {

}
