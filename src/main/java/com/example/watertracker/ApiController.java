package com.example.watertracker;

import com.example.watertracker.db.DrinkRecord;
import com.example.watertracker.db.DrinkRepository;
import com.example.watertracker.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api")
public class ApiController {

    @Autowired
    private DrinkRepository drinkRepo;
    @Autowired
    private UserRepository userRepo;

    @GetMapping(path="/login")
    public @ResponseBody
    String logUserIn(@RequestParam(defaultValue = "false") Boolean err) {
        String result;
        if (err) {
            result = "Wrong username or password.";
        } else {
            result = "Login first.";
        }
        return result;
    }

    @GetMapping(path="/success")
    public @ResponseBody
    String successfulAction() {
        return "Action was performed successfully.";
    }

    @PostMapping(path="/record")
    public @ResponseBody
    String addNewRecord (@RequestParam Integer volume, String nick, DrinkRecord.Type_of_drink type){
        return RequestHandler.saveRecord(volume, userRepo.findByUsername(nick), type, drinkRepo);
    }

    @PostMapping(path="/public/sign")
    public @ResponseBody
    String addNewUser (@RequestParam String username, String password, Model model){
        return RequestHandler.saveUser(username, password, userRepo);
    }

}
