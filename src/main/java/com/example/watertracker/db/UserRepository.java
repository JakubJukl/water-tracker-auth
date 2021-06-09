package com.example.watertracker.db;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.lang.NonNull;

/* Class that draws (represents) data out of our db
 * We can access endpoints starting by '/users' (actually it's '/api/users'
 * because I changed the default path in application.properties)
 */
@RepositoryRestResource(collectionResourceRel = "content", path = "users")
public interface UserRepository extends JpaRepository<User, String> {

    // I don't want users to be able to delete records from an endpoint
    @Override
    @RestResource(exported = false)
    void delete(@NonNull User user);

    /*
     * Users shouldn't be able to PUT/PATCH/POST directly into the 'table'
     * (I know it's actually CrudRepository, but simplification never really
     * killed anyone. Right?)
     */
    @NotNull
    @Override
    @RestResource(exported = false)
    <S extends User> S save(@NonNull S  user);

    /*
     * Users don't see this endpoint, since it's irrelevant for them
     * but it's used internally. Query statement is used here as an example.
     */
    @RestResource(exported = false)
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);


}
