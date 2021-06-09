package com.example.watertracker;

import com.example.watertracker.db.DrinkRecord;
import com.example.watertracker.db.DrinkRepository;
import com.example.watertracker.db.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("api")
public class ApiController {

    @Autowired
    private DrinkRepository drinkRepo;
    @Autowired
    private UserRepository userRepo;

    @Hidden
    @GetMapping(path="/login")
    public @ResponseBody
    void logUserIn(@RequestParam(defaultValue = "false") Boolean err) {
        if (err) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RequestHandler.LOGIN_ERROR);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Login first.");
        }
    }
    @Hidden
    @GetMapping(path="/success")
    public @ResponseBody
    String successfulAction() {
        return "Action was performed successfully.";
    }

    @PostMapping(path="/record")
    public @ResponseBody
    String addNewRecord (@RequestParam Integer volume, DrinkRecord.Type_of_drink type, Principal principal) {
        String message = RequestHandler.saveRecord(volume, userRepo.findByUsername(principal.getName()), type, drinkRepo);
        if (message.equals(RequestHandler.SAVE_OK)) {
            return message;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @PostMapping(path="/public/sign")
    public @ResponseBody
    String addNewUser (@RequestParam String username, String password){
        String message = RequestHandler.saveUser(username, password, userRepo);
        if (message.equals(RequestHandler.REGISTRATION_OK)){
            return message;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

}
