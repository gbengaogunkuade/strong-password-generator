package com.ogunkuade.passwordgenerator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.ui.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class PasswordService {

    Logger logger = LoggerFactory.getLogger(PasswordService.class);

    String totalElements;
    String generatedPassword;
    private String passKey;
    String question1;
    String question2;
    Integer convertedQuestion1;
    Integer convertedQuestion2;
    Integer originalAnswer;
    Integer convertedUserAnswer;


    public Integer generatedQuestionAnswer(){
        question1 = RandomStringUtils.randomNumeric(1, 2);
        question2 = RandomStringUtils.randomNumeric(1, 2);
        convertedQuestion1 = Integer.valueOf(question1);
        convertedQuestion2 = Integer.valueOf(question2);
        return convertedQuestion1 + convertedQuestion2;
    }


    public String generateHardPassword(Integer length, Boolean num, Boolean special){
        String bigLetters = "ABCDEFGHIJKLMNOPQRSTUVWYZ";
        String smallLetters = "abcdefghijklmnopqrstuvwxyz";
        int numbers = 1234567890;
        String specialCharacters = "!@/(,)#$&*_+{}[]?~";
        totalElements = bigLetters + smallLetters;
        if(num){
            totalElements += numbers;
        }
        if(special){
            totalElements += specialCharacters;
        }
        generatedPassword = RandomStringUtils.random(length, totalElements);
        return generatedPassword;
    }


    public String generatePasswordPage(Model model){
        originalAnswer = generatedQuestionAnswer();
        model.addAttribute("passKey", passKey);
        model.addAttribute("question1", question1);
        model.addAttribute("question2", question2);
        return "index";
    }


    public String generatePasswordPageResult(Integer length, Boolean numbers, Boolean specialCharacters, String userAnswer, Model model) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(userAnswer);

        if(matcher.find()){
            convertedUserAnswer = Integer.valueOf(userAnswer);
            if(convertedUserAnswer.equals(originalAnswer)){
                passKey = generateHardPassword(length, numbers, specialCharacters);
                logger.info("STRONG PASSWORD SUCCESSFULLY GENERATED");
                return "redirect:/?success";
            } else{
                logger.warn("USER COULD NOT PROVE TO BE A HUMAN");
                return "redirect:/?failed";
            }
        } else{
            logger.error("USER PROVIDED A STRING INSTEAD OF A DIGIT");
            return "redirect:/?failed";
        }

    }




}
