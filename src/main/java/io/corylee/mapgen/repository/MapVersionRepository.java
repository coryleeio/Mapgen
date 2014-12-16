package io.corylee.mapgen.repository;

import io.corylee.mapgen.domain.MapVersion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the MapVersion entity.
 */
public interface MapVersionRepository extends JpaRepository<MapVersion, Long> {

}
