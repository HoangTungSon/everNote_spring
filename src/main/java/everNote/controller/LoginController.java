package everNote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/admin")
    public String admin(Principal principal) {
        // Get authenticated user name from Principal
        System.out.println(principal.getName());
        return "/loginPage/admin";
    }

    @GetMapping("/dba")
    public String dba(Principal principal) {
        // Get authenticated user name from Principal
        System.out.println(principal.getName());
        return "/loginPage/dba";
    }

    @GetMapping("/")
    public String index(){
        return "/loginPage/index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/loginPage/loginForm";
    }
}
