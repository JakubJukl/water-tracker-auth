package com.example.watertracker;

import com.example.watertracker.db.DrinkRecord;
import com.example.watertracker.db.DrinkRepository;
import com.example.watertracker.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.method.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/*
 * This controller handles MVC. So everything normal user would
 * visit (I don't really know how normal internet user looks like)
 */
@Controller
public class MainController {

    // Actual 'drinks' table data
    @Autowired
    private DrinkRepository drinkRepo;

    // Actual 'users' table data
    @Autowired
    private UserRepository userRepo;

    /*
     * This one is called before the exception in ExceptHandler
     * if user tries to scam us by submitting incompatible data types
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleTypeMisMatch(HttpServletRequest req, MethodArgumentTypeMismatchException ex) {
        ModelAndView mav = new ModelAndView();
        String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        String message = String.format("Field '%s' must be a valid %s.", name, type);
        mav.addObject("message", message);
        mav.setViewName(req.getServletPath());
        return mav;
    }

    /*
     * Same as exception above. Triggers if user doesn't submit a value.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingPara(HttpServletRequest req, MissingServletRequestParameterException ex) {
        ModelAndView mav = new ModelAndView();
        String name = ex.getParameterName();
        String message = String.format("Parameter '%s' can't be empty.", name);
        mav.addObject("message", message);
        mav.setViewName(req.getServletPath());
        return mav;
    }

    /*
     * Page, where users upload data is also an index page
     */
    @GetMapping(path = {"", "/record"})
    public String record() {
        return "record";
    }

    /*
     * 'record' template POSTs the data here
     * I could have made it POST the data without reloading the page
     * and display the answer from '/api' with JQuery, but there
     * is nothing wrong with mvc to display pages imo
     */
    @PostMapping(path={"","/record"})
    public String addNewRecord (@RequestParam Integer volume,
      DrinkRecord.Type_of_drink type, Model model, Principal principal){
        // Returns message depending, if everything worked out well
        model.addAttribute("message", RequestHandler.saveRecord(volume, userRepo.findByUsername(principal.getName()), type, drinkRepo));
        return "record";
    }

    /*
     * Nice page that uses Google Charts to create charts
     * I didn't invest much time into it, only the means
     * necessary for proof of concept
     */
    @GetMapping(path="/chart")
    public String chart() {
        return "chart";
    }

    /*
     * Custom login page with MVC displaying error
     * if wrong credentials were used.
     */
    @GetMapping(path={"/login", "/public/login"})
    public String login(@RequestParam(defaultValue = "false") Boolean err, Model model) {
        if (err){
            model.addAttribute("message", RequestHandler.LOGIN_ERROR);
        }
        return "login";
    }

    /*
     * Custom sign up page.
     */
    @GetMapping(path="/public/sign")
    public String sign() {
        return "signup";
    }

    /*
     * POST method for signing up new users.
     */
    @PostMapping(path="public/sign")
    public String addNewUser (@RequestParam String username, String password, Model model){
        // saveUser returns string if the registration was successful or not and adds it to model
        String message = RequestHandler.saveUser(username, password, userRepo);
        model.addAttribute("message", message);
        if (message.equals(RequestHandler.REGISTRATION_OK)){
            // Display login page and an OK message
            return "login";
        }else{
            // load signup page with an error message
            return "signup";
        }
    }
}
