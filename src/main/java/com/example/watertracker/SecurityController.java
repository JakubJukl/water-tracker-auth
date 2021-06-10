package com.example.watertracker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/*
 * Controller that should give access to everything
 * security related. Right now it only offers endpoint
 * for username of logged in user.
 */
@Controller
public class SecurityController {

    /*
     * Users can check through API endpoint, on which account they are
     * logged in at the moment.
     */
    @GetMapping(value = "/api/username")
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }


}
