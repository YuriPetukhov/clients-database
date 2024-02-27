package com.unibell.clientsdatabase.validation;

import com.unibell.clientsdatabase.entity.Contact;
import com.unibell.clientsdatabase.enums.ContactType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ContactValidator implements ConstraintValidator<ValidContact, Contact> {

    @Override
    public void initialize(ValidContact constraintAnnotation) {
    }

    @Override
    public boolean isValid(Contact contact, ConstraintValidatorContext context) {
        if (contact == null) {
            return false;
        }

        if (contact.getType() == ContactType.EMAIL) {
            return isValidEmail(contact.getTypeValue());
        } else if (contact.getType() == ContactType.PHONE) {
            return isValidPhoneNumber(contact.getTypeValue());
        }

        return false;
    }

    private boolean isValidEmail(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regexPattern = "^\\+7\\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$";
        return Pattern.compile(regexPattern)
                .matcher(phoneNumber)
                .matches();
    }
}
