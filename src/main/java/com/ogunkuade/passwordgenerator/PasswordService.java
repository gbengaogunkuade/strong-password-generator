package com.ogunkuade.passwordgenerator;


import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.ui.Model;
import java.util.regex.Pattern;


@Service
public class PasswordService {

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
        boolean matches = Pattern.matches("\\d+", userAnswer);
        if(matches){
            convertedUserAnswer = Integer.valueOf(userAnswer);
            if(convertedUserAnswer.equals(originalAnswer)){
                passKey = generateHardPassword(length, numbers, specialCharacters);
                return "redirect:/?success";
            } else{
                return "redirect:/?failed";
            }
        } else{
            System.out.println("not cool");
            return "redirect:/?failed";
        }

    }




}
