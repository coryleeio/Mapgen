package io.corylee.mapgen.repository;

import io.corylee.mapgen.domain.Edge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EdgeRepository extends JpaRepository<Edge, Long> {

}
