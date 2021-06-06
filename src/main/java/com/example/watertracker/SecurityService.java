package com.example.watertracker;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
