package com.example.watertracker.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * Declaring class, that makes our "drinks" table and holds all drink records from users.
 */
@Entity
@Table(name = "drinks")
public class DrinkRecord {

    // Automatically incremented ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // How much did user drink
    private int volume;

    // Time of record creation (not UTC), but time zone specific
    private LocalDateTime created = LocalDateTime.now();

    /* Every user has many drink records, but every drink record
       has only one user. Furthermore it has to be ignored by json
       otherwise it will be stuck in an infinite loop. */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="username", nullable = false)
    private User user;

    // What type of drink user drank
    private Type_of_drink drinkType;

    // Enum with possible types
    public enum Type_of_drink {
        WATER,
        ALCOHOL,
        SWEET,
        OTHER
    }

    // Auto generated getters and setters
    public Type_of_drink getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(Type_of_drink type) {
        this.drinkType = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public LocalDateTime getCreated() {
        return created;
    }

}
