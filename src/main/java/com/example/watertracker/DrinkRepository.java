package com.example.watertracker;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RepositoryRestResource(collectionResourceRel = "test", path = "test")
public interface DrinkRepository extends PagingAndSortingRepository<DrinkRecord, Long> {
    @GetMapping(path="/test/{nick}")
    List<DrinkRecord> findByNick(@PathVariable("nick") String nick);

    List<DrinkRecord> findByVolume(@Param("volume") int volume);

    List<DrinkRecord> findByVolumeAndNick(@Param("volume") int volume, String nick);

}