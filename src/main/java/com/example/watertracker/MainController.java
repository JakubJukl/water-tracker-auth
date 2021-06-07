package com.example.watertracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.method.annotation.*;
import org.springframework.security.core.context.*;

@Controller
public class MainController {
    @Autowired
    private DrinkRepository drinkRepo;
    @Autowired
    private UserRepository userRepo;


    @PostMapping(path="/add")
    public @ResponseBody String addNewRecord (@RequestParam Integer volume,
    String nick, DrinkRecord.Type_of_drink type){
    if (volume <= 0 || nick.isBlank()){
        throw new IllegalArgumentException("All fields must be filled with valid values.");
    }
    DrinkRecord n = new DrinkRecord();
    n.setVolume(volume);
    n.setNick(nick);
    n.setType(type);
    drinkRepo.save(n);
    return "Saved";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        String message = String.format("'%s' must be a valid %s.", name, type);
        String title = "ERROR: 400";
        model.addAttribute("message", message);
        model.addAttribute("title", title);
        return "error";
    }


    @GetMapping(path="/all")
    public @ResponseBody Iterable<DrinkRecord> getAllUsers() {
        // This returns a JSON or XML with the users
        return drinkRepo.findAll();
    }

    @GetMapping(path="/all/{nick}")
    public @ResponseBody
    Iterable<DrinkRecord> getSpecificUser(@PathVariable("nick") String nick) {
        // This returns a JSON or XML with the users
        return drinkRepo.findByNick(nick);
    }

    @GetMapping(path="/all/{nick}/{volume}")
    public @ResponseBody
    Iterable<DrinkRecord> getUserVolume(@PathVariable("nick") String nick) {
        // This returns a JSON or XML with the users
        return drinkRepo.findByNick(nick);
    }

    @GetMapping(path = {"", "/record"})
    public String record() {
        return "record";
    }

    @GetMapping(path="/chart")
    public String chart() {
        return "chart";
    }

    @GetMapping(path={"/login", "/public/login"})
    public String login(@RequestParam(defaultValue = "false") Boolean err, Model model) {
        model.addAttribute("err", err);
        return "login";
    }

    @GetMapping(path="/public/sign")
    public String sign(@RequestParam(defaultValue = "false") Boolean err, Model model) {
        model.addAttribute("err", err);
        return "signup";
    }

    @PostMapping(path="public/signuser")
    public String addNewUser (@RequestParam String username, String password){
        if (username.isBlank() || password.isBlank()){
            throw new IllegalArgumentException("All fields must be filled with valid values.");
        }
        if (userRepo.findByUsername(username) != null){
            return "redirect:sign?err=true";
        }
        User n = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        n.setPassword(encodedPassword);
        n.setUsername(username);
        userRepo.save(n);
        return "redirect:login";
    }

}
