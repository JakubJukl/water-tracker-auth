package com.example.watertracker;

import com.example.watertracker.db.DrinkRecord;
import com.example.watertracker.db.DrinkRepository;
import com.example.watertracker.db.User;
import com.example.watertracker.db.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RequestHandler {

    public static String saveRecord(Integer volume, User user, DrinkRecord.Type_of_drink type,
                                    DrinkRepository drinkRepo){
        String message = "Saved successfully.";
        if (volume <= 0){
            message = "All fields must be filled with valid values.";
        } else{
            DrinkRecord n = new DrinkRecord();
            n.setVolume(volume);
            n.setUser(user);
            n.setType(type);
            drinkRepo.save(n);
        }
        return message;
    }

    public static String saveUser(String username, String password, UserRepository userRepo){
        String message = "Registration succeeded.";
        if (username.isBlank() || password.isBlank()){
            message = "All fields must be filled with valid values.";
        } else if (userRepo.findByUsername(username) != null){
            message = "Username is already taken.";
        }else {
            User n = new User();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            n.setPassword(encodedPassword);
            n.setUsername(username);
            userRepo.save(n);
        }
        return message;
    }
}
