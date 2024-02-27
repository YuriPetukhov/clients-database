package com.unibell.clientsdatabase.validation;


import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class PhoneRusNumberValidator implements Validator {
    @Override
    public boolean validate(String value) {
        String regexPattern = "^\\+7\\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$";
        return Pattern.compile(regexPattern)
                .matcher(value)
                .matches();
    }
}
