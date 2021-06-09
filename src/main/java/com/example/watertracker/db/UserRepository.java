package com.example.watertracker.db;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.lang.NonNull;

@RepositoryRestResource(collectionResourceRel = "content", path = "users")
public interface UserRepository extends JpaRepository<User, String> {

    @Override
    @RestResource(exported = false)
    void delete(@NonNull User user);

    @NotNull
    @Override
    @RestResource(exported = false)
    <S extends User> S save(@NonNull S  user);


    @RestResource(exported = false)
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);


}
