package com.example.watertracker.db;

import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

/* Class that draws (represents) data out of our db
 * We can access endpoints starting by '/records' (actually it's '/api/records'
 * because I changed the default path in application.properties).
 */
@RepositoryRestResource(collectionResourceRel = "content", path = "records")
public interface DrinkRepository extends JpaRepository<DrinkRecord, Long> {

    // I don't want users to be able to delete records from an endpoint
    @Override
    @RestResource(exported = false)
    void delete(@NonNull DrinkRecord record);

    /*
     * Users shouldn't be able to PUT/PATCH/POST directly into the 'table'
     * (I know it's actually CrudRepository, but simplification never really
     * killed anyone. Right?)
     */
    @NotNull
    @Override
    @RestResource(exported = false)
    <S extends DrinkRecord> S save(@NonNull S  drinkRecord);

    /* Bunch of methods that directly query the database (table/repository)
       I listed only few, that I find interesting, but the possibilities are endless
       They can be renamed or made to be as a standard query statement */
    List<DrinkRecord> findByCreatedAfter(@Param("date") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) LocalDateTime date);

    List<DrinkRecord> findByCreatedBefore(@Param("date") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) LocalDateTime date);

    List<DrinkRecord> findByCreatedBetween(@Param("start") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @Param("end") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    List<DrinkRecord> findByDrinkType(@Param("type") DrinkRecord.Type_of_drink type);

    List<DrinkRecord> findByDrinkTypeOrDrinkType(@Param("type1") DrinkRecord.Type_of_drink type1,
                                       @Param("type2") DrinkRecord.Type_of_drink type2);

    List<DrinkRecord> findByCreatedBetweenAndDrinkType(
            @Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @Param("type") DrinkRecord.Type_of_drink type);
}