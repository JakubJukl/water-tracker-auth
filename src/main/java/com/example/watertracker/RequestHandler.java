package com.example.watertracker;

import com.example.watertracker.db.DrinkRecord;
import com.example.watertracker.db.DrinkRepository;
import com.example.watertracker.db.User;
import com.example.watertracker.db.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class RequestHandler {

    public static final String VOLUME_ERROR = "Volume must be a valid integer.";
    public static final String TYPE_ERROR = "Type of drink must be valid type of drink.";
    public static final String USERNAME_ERROR = "Username is already taken.";
    public static final String BLANK_ERROR = "All fields must be filled with valid values.";
    public static final String SAVE_OK = "Saved successfully.";
    public static final String REGISTRATION_OK = "Registration succeeded.";
    public static final String LOGIN_ERROR = "Wrong username or password.";

    public static String saveRecord(Integer volume, User user, DrinkRecord.Type_of_drink type,
                                    DrinkRepository drinkRepo){
        String message = SAVE_OK;
        if (volume <= 0) {
            message = VOLUME_ERROR;
        } else if (type == null){
            message = TYPE_ERROR;
        } else{
            DrinkRecord n = new DrinkRecord();
            n.setVolume(volume);
            n.setUser(user);
            n.setDrinkType(type);
            drinkRepo.save(n);
        }
        return message;
    }

    public static String saveUser(String username, String password, UserRepository userRepo){
        String message = REGISTRATION_OK;
        if (username.isBlank() || password.isBlank()){
            message = BLANK_ERROR;
        } else if (userRepo.findByUsername(username) != null){
            message = USERNAME_ERROR;
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
