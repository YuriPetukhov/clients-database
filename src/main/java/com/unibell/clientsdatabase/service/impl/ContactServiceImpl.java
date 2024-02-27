package com.unibell.clientsdatabase.service.impl;

import com.unibell.clientsdatabase.dto.ClientContact;
import com.unibell.clientsdatabase.dto.ClientContactByType;
import com.unibell.clientsdatabase.entity.Client;
import com.unibell.clientsdatabase.entity.Contact;
import com.unibell.clientsdatabase.enums.ContactType;
import com.unibell.clientsdatabase.exception.ClientNotFoundException;
import com.unibell.clientsdatabase.mapper.ContactMapper;
import com.unibell.clientsdatabase.repository.ContactRepository;
import com.unibell.clientsdatabase.service.ClientService;
import com.unibell.clientsdatabase.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing contacts.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ClientService clientService;
    private final ContactMapper contactMapper;

    /**
     * Adds a new contact to a client.
     *
     * @param clientId the client ID
     * @param type the type of contact (EMAIL or PHONE)
     * @param value the value of the contact (email address or phone number)
     * @throws ClientNotFoundException if the client is not found
     */
    @Override
    public void addNewContact(Long clientId, ContactType type, String value) {
        Optional<Client> clientOpt = clientService.findClientById(clientId);

        if (clientOpt.isEmpty()) {
            throw new ClientNotFoundException("Client not found with id: " + clientId);
        }

        Contact newContact = new Contact();
        newContact.setClient(clientOpt.get());
        newContact.setType(type);
        newContact.setTypeValue(value);
        contactRepository.save(newContact);
    }

    /**
     * Gets all contacts for a client.
     *
     * @param clientId the client ID
     * @return the list of client contacts
     */
    @Override
    public List<ClientContact> getClientContacts(Long clientId) {
        List<Contact> contacts = contactRepository.findAllByClientId(clientId);
        return contacts.stream()
                .map(contactMapper::toDtoClientContact)
                .collect(Collectors.toList());
    }

    /**
     * Gets all contacts for a client by type.
     *
     * @param clientId the client ID
     * @param type the type of contact (EMAIL or PHONE)
     * @return the list of client contacts
     */
    @Override
    public List<ClientContactByType> getClientContacts(Long clientId, ContactType type) {
        List<Contact> contacts = contactRepository.findAllByClientIdAndType(clientId, type);
        return contacts.stream()
                .map(contactMapper::toDtoClientContactByType)
                .collect(Collectors.toList());
    }
}
