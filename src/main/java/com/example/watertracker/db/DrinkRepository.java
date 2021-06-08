package com.example.watertracker.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "content", path = "records")
public interface DrinkRepository extends JpaRepository<DrinkRecord, Long> {

    List<DrinkRecord> findByVolume(@Param("volume") int volume);


}