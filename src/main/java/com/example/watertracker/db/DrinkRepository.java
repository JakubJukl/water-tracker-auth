package com.example.watertracker.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

@RepositoryRestResource(collectionResourceRel = "content", path = "records")
public interface DrinkRepository extends JpaRepository<DrinkRecord, Long> {

    List<DrinkRecord> findByCreatedAfter(@Param("date") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) LocalDateTime date);

    List<DrinkRecord> findByCreatedBefore(@Param("date") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) LocalDateTime date);

    List<DrinkRecord> findByCreatedBetween(@Param("start") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @Param("end") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    List<DrinkRecord> findByType(@Param("type") DrinkRecord.Type_of_drink type);

    DrinkRepository findByTypeAndVolume(@Param("type") DrinkRecord.Type_of_drink type, @Param("volume") Integer volume);

    List<DrinkRecord> findByTypeOrType(@Param("type1") DrinkRecord.Type_of_drink type1,
                                       @Param("type2") DrinkRecord.Type_of_drink type2);
}