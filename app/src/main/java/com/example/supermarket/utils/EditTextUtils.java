package com.example.supermarket.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTextUtils {

    public static final String PATTERN_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$";
    public static final String PATTERN_EMAL = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(PATTERN_EMAL, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password){
        Pattern pattern = Pattern.compile(PATTERN_PASSWORD, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
