package com.navi.mynewsservice.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateService {


    private Set<String> countries = new HashSet<>(Set.of("de", "hk", "tw", "pt", "lt", "lv", "ua", "hu", "ma",
            "id", "ie", "us", "eg", "il", "ae", "in", "za", "it", "mx", "my", "ve", "ar", "at", "au", "ng",
            "ro", "nl", "no", "rs", "be", "ru", "bg", "jp", "fr", "nz", "sa", "br", "se", "sg", "si", "sk",
            "gb", "ca", "ch", "kr", "cn", "gr", "co", "cu", "th", "cz", "ph", "pl", "tr"));
    private Set<String> categories = new HashSet<>(Set.of("business", "entertainment", "general", "health",
            "science", "sports", "technology"));


    public boolean validateCountry(String country){
        if (countries.contains(country)) return true;
        return false;
    }

    public boolean validateCategory(String category){
        if (categories.contains(category)) return true;
        return false;
    }

    public boolean validateDate(String date){
        LocalDate fromDate = LocalDate.parse(date); // Replace with your 'from' date
        LocalDate currentDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(fromDate, currentDate);

        if (daysBetween <= 30) return false;
        return true;

    }

    public boolean validateEmail(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
