package com.unibell.clientsdatabase.validation;

import com.unibell.clientsdatabase.entity.Contact;
import com.unibell.clientsdatabase.enums.ContactType;
import com.unibell.clientsdatabase.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactUniqueValidator {

    private final ContactRepository contactRepository;

    public boolean isContactUniqueForClient(Long clientId, ContactType type, String value) {
        Contact existingContact = contactRepository.findByClientIdAndTypeAndValue(clientId, type, value);
        return existingContact == null;
    }
}
