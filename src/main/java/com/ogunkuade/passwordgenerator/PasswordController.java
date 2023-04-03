package com.ogunkuade.passwordgenerator;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class PasswordController {

    private PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }


    @GetMapping("/")
    public String generatingPasswordPage(Model model){
        return passwordService.generatePasswordPage(model);
    }


    @PostMapping("/")
    public String generatingPasswordPageResult(
            @RequestParam Integer length,
            @RequestParam(required = false, defaultValue = "false") Boolean numbers,
            @RequestParam(required = false, defaultValue = "false") Boolean specialCharacters,
            @RequestParam String userAnswer, Model model
    ){
        return passwordService.generatePasswordPageResult(length, numbers, specialCharacters, userAnswer, model);
    }





}
