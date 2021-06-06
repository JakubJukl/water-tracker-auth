package com.example.watertracker;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "drinks")
public class DrinkRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nick;
    private int volume;
    private LocalDateTime created = LocalDateTime.now();

    public Type_of_drink getType() {
        return type;
    }

    public void setType(Type_of_drink type) {
        this.type = type;
    }

    private Type_of_drink type;

    public enum Type_of_drink {
        WATER,
        ALCOHOL,
        SWEET,
        OTHER
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
