package com.example.watertracker;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
