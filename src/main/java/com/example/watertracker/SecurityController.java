package com.example.watertracker;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class SecurityController {

    @Hidden
    @GetMapping(value = "/api/username")
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }


}
