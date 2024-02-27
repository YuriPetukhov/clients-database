package com.unibell.clientsdatabase.service;

import com.unibell.clientsdatabase.dto.ClientContact;
import com.unibell.clientsdatabase.dto.ClientContactByType;
import com.unibell.clientsdatabase.enums.ContactType;

import java.util.List;

public interface ContactService {

    void addNewContact(Long clientId, ContactType type, String value);
    List<ClientContact> getClientContacts(Long clientId);

    List<ClientContactByType> getClientContacts(Long clientId, ContactType type);
}
