package com.example.watertracker.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/*
 * Class that stores records of registered users in db.
 */
@Entity
@Table(name = "users")
public class User {

    // Every user has unique username
    @Id
    private String username;

    // We don't want password to be shown in json
    @JsonIgnore
    private String password;

    // Every user has many drink records
    @OneToMany(mappedBy="user")
    private Set<DrinkRecord> records;

    // Automatically generated getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<DrinkRecord> getRecords() {
        return records;
    }

    public void setRecords(Set<DrinkRecord> records) {
        this.records = records;
    }
}
