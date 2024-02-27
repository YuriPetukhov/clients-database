package com.unibell.clientsdatabase.validation;

import com.unibell.clientsdatabase.enums.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContactInputValidator {


    private final Map<ContactType, Validator> validationStrategies;

    @Autowired
    public ContactInputValidator(EMailValidator eMailValidator,
                                 PhoneRusNumberValidator phoneRusNumberValidator) {
        validationStrategies = Map.of(ContactType.EMAIL, eMailValidator,
                ContactType.PHONE, phoneRusNumberValidator);
    }

    public boolean validate(ContactType type, String value) {
        return validationStrategies.get(type).validate(value);
    }
}
