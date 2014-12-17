package io.corylee.mapgen.repository;

import io.corylee.mapgen.domain.Corner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CornerRepository extends JpaRepository<Corner, Long> {

}
