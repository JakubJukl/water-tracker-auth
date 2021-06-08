package com.example.watertracker.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    @JsonIgnore
    private String password;
    @OneToMany(mappedBy="user")
    private Set<DrinkRecord> records;

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
