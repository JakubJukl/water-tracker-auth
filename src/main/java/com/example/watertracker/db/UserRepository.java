package com.example.watertracker.db;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "content", path = "users")
public interface UserRepository extends JpaRepository<User, String> {

    @Override
    @RestResource(exported = false)
    void delete(User user);

    @Override
    @RestResource(exported = false)
    User save(User record);


    @RestResource(exported = false)
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);


}
