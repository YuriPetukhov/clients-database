package com.unibell.clientsdatabase.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EMailValidator implements Validator {
    @Override
    public boolean validate(String value) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(value)
                .matches();
    }
}


