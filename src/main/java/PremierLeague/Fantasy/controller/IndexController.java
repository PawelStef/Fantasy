package PremierLeague.Fantasy.controller;

import PremierLeague.Fantasy.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class IndexController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String index(Model model, Principal principal){
        if(principal!= null){
            model.addAttribute("img", accountService.findPhotoByUsername(principal.getName()));
        }
        return "index";
    }

    @GetMapping("/tylkodlakozakow")
    public String kozaki(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login-form";
    }

    @GetMapping("/profilePhoto")
    @PreAuthorize(value = "isAuthenticated()")
    public String profilePhoto(){
        return "photo-form";
    }

    @PostMapping("/profilePhoto")
    @PreAuthorize(value = "isAuthenticated()")
    public String uploadPhoto(Principal principal, @RequestParam("photo") MultipartFile photo){
        accountService.savePhotoFor(principal.getName(), photo);
        return "redirect:/";
    }
}
