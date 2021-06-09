package com.example.watertracker;

import com.example.watertracker.db.DrinkRecord;
import com.example.watertracker.db.DrinkRepository;
import com.example.watertracker.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.method.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private DrinkRepository drinkRepo;
    @Autowired
    private UserRepository userRepo;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
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


    @GetMapping(path = {"", "/record"})
    public String record() {
        return "record";
    }

    @PostMapping(path={"","/record"})
    public String addNewRecord (@RequestParam Integer volume,
      DrinkRecord.Type_of_drink type, Model model, Principal principal){
        model.addAttribute("message", RequestHandler.saveRecord(volume, userRepo.findByUsername(principal.getName()), type, drinkRepo));
        return "record";
    }

    @GetMapping(path="/chart")
    public String chart() {
        return "chart";
    }

    @GetMapping(path={"/login", "/public/login"})
    public String login(@RequestParam(defaultValue = "false") Boolean err, Model model) {
        if (err){
            model.addAttribute("message", "Wrong username or password.");
        }
        return "login";
    }

    @GetMapping(path="/public/sign")
    public String sign() {
        return "signup";
    }

    @PostMapping(path="public/sign")
    public String addNewUser (@RequestParam String username, String password, Model model){
        String message = RequestHandler.saveUser(username, password, userRepo);
        model.addAttribute("message", message);
        if (message.equals("Registration succeeded.")){
            return "login";
        }else{
            return "signup";
        }
    }

    @GetMapping(path = "/id")
    @ResponseBody
    public String test(){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actor Not Found");
    }



}
