package com.example.watertracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.method.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private DrinkRepository drinkRepo;
    @Autowired
    private UserRepository userRepo;


    @PostMapping(path="api/add")
    public @ResponseBody String addNewRecord (@RequestParam Integer volume,
    String nick, DrinkRecord.Type_of_drink type){
    return refactorRecord(volume, nick, type);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleError(HttpServletRequest req, MethodArgumentTypeMismatchException ex) {
        ModelAndView mav = new ModelAndView();
        String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        String message = String.format("Field '%s' must be a valid %s.", name, type);
        mav.addObject("message", message);
        mav.setViewName(req.getServletPath());
        return mav;
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

    @PostMapping(path={"","/record"})
    public String addNewRecord1 (@RequestParam Integer volume,
      DrinkRecord.Type_of_drink type, Model model, Principal principal){
        model.addAttribute("message", refactorRecord(volume, principal.getName(), type));
        return "record";
    }

    public String refactorRecord(Integer volume, String nick, DrinkRecord.Type_of_drink type){
        String message = "Saved successfully.";
        if (volume <= 0 || nick.isBlank()){
            message = "All fields must be filled with valid values.";
        } else{
            DrinkRecord n = new DrinkRecord();
            n.setVolume(volume);
            n.setNick(nick);
            n.setType(type);
            drinkRepo.save(n);
        }
        return message;
    }

    @GetMapping(path="/chart")
    public String chart() {
        return "chart";
    }

    @GetMapping(path={"/login", "/public/login"})
    public String login(@RequestParam(defaultValue = "false") Boolean err, Model model) {
        if (err){
            model.addAttribute("message", "Wrong user or password.");
        }
        return "login";
    }

    @GetMapping(path="/public/sign")
    public String sign() {
        return "signup";
    }

    @PostMapping(path="public/sign")
    public String addNewUser (@RequestParam String username, String password, Model model){
        String message = refactorAddUser(username, password);
        model.addAttribute("message", message);
        if (message.equals("Registration succeeded.")){
            return "login";
        }else{
            return "signup";
        }

    }

    public String refactorAddUser(String username, String password){
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
