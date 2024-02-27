package com.unibell.clientsdatabase.service.impl;

import com.unibell.clientsdatabase.dto.ClientContact;
import com.unibell.clientsdatabase.dto.ClientContactByType;
import com.unibell.clientsdatabase.entity.Client;
import com.unibell.clientsdatabase.entity.Contact;
import com.unibell.clientsdatabase.enums.ContactType;
import com.unibell.clientsdatabase.exception.ClientNotFoundException;
import com.unibell.clientsdatabase.exception.DuplicateContactException;
import com.unibell.clientsdatabase.exception.InvalidContactException;
import com.unibell.clientsdatabase.mapper.ContactMapper;
import com.unibell.clientsdatabase.repository.ContactRepository;
import com.unibell.clientsdatabase.service.ClientService;
import com.unibell.clientsdatabase.service.ContactService;
import com.unibell.clientsdatabase.validation.ContactUniqueValidator;
import com.unibell.clientsdatabase.validation.ContactInputValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ClientService clientService;
    private final ContactMapper contactMapper;
    private final ContactInputValidator contactInputValidator;
    private final ContactUniqueValidator contactUniqueValidator;
    @Override
    public void addNewContact(Long clientId, ContactType type, String value) {
        Optional<Client> clientOpt = clientService.findClientById(clientId);

        if (clientOpt.isEmpty()) {
            throw new ClientNotFoundException("Client not found with id: " + clientId);
        }

        if (!contactUniqueValidator.isContactUniqueForClient(clientId, type, value)) {
            throw new DuplicateContactException("Contact already exists for this client");
        }

        if (!contactInputValidator.validate(type, value)) {
            throw new InvalidContactException("Invalid contact type or value: " + type + ", " + value);
        }

        Contact newContact = new Contact();
        newContact.setClient(clientOpt.get());
        newContact.setType(type);
        newContact.setValue(value);
        contactRepository.save(newContact);
    }

    @Override
    public List<ClientContact> getClientContacts(Long clientId) {
        List<Contact> contacts = contactRepository.findAllByClientId(clientId);
        return contacts.stream()
                .map(contactMapper :: toDtoClientContact)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientContactByType> getClientContacts(Long clientId, ContactType type) {
        List<Contact> contacts = contactRepository.findAllByClientIdAndType(clientId, type);
        return contacts.stream()
                .map(contactMapper :: toDtoClientContactByType)
                .collect(Collectors.toList());
    }
}
