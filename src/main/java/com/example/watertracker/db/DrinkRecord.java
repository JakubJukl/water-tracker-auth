package com.example.watertracker.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "drinks")
public class DrinkRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private int volume;
    private LocalDateTime created = LocalDateTime.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="username", nullable = false)
    private User user;

    public Type_of_drink getType() {
        return type;
    }

    public void setType(Type_of_drink type) {
        this.type = type;
    }

    private Type_of_drink type;

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

    public enum Type_of_drink {
        WATER,
        ALCOHOL,
        SWEET,
        OTHER
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
