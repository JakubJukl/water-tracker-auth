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

/*
 * Controller that takes care of "everything" happening on '/api' path.
 * (Not everything at all, since REST repository controllers operate there
 * as well, however this one can take care of POST requests.
*/
@Controller
@RequestMapping("api")
public class ApiController {

    //Actual 'drinks' table data
    @Autowired
    private DrinkRepository drinkRepo;

    //Actual 'users' table data
    @Autowired
    private UserRepository userRepo;

    /*
     * There is no need to display this api endpoint, since the only time
     * you need to see a login page, is when you are not logged in and
     * spring security takes care of that
     */
    @Hidden
    @GetMapping(path="/login")
    public @ResponseBody
    void logUserIn(@RequestParam(defaultValue = "false") Boolean err) {
        if (err) {
            //If user tries to log in via api with bad credentials, he gets an error
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RequestHandler.LOGIN_ERROR);
        } else {
            //If user tries to access non public path and gets redirected
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Login first.");
        }
    }

    /*
     * Once again no need to display this endpoint, it serves just
     * as an assurance, that login/logout was successful
     */
    @Hidden
    @GetMapping(path="/success")
    public @ResponseBody
    String successfulAction() {
        return "Action was performed successfully.";
    }

    /*
     * Sending drink records via POST. Is the pathing too confusing?
     * It's easy, '/records' is for GET requests and '/record' for POST
     */
    @PostMapping(path="/record")
    public @ResponseBody
    String addNewRecord (@RequestParam Integer volume, DrinkRecord.Type_of_drink type, Principal principal) {
        /* This function processes all the credentials and returns a message. It might be faster if
           I had made a dictionary and passed the keys instead of the whole message.*/
        String message = RequestHandler.saveRecord(volume, userRepo.findByUsername(principal.getName()), type, drinkRepo);
        if (message.equals(RequestHandler.SAVE_OK)) {
            return message;
        } else {
            //If the message is not ok, throw ERROR http status
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    /*
     * It would be a shame, if user couldn't sign in via API
     */
    @PostMapping(path="/public/sign")
    public @ResponseBody
    String addNewUser (@RequestParam String username, String password){
        // Similar approach as with addNewRecord above.
        String message = RequestHandler.saveUser(username, password, userRepo);
        if (message.equals(RequestHandler.REGISTRATION_OK)){
            return message;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

}
