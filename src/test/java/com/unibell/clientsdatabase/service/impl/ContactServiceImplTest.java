package com.unibell.clientsdatabase.service.impl;

import com.unibell.clientsdatabase.dto.ClientContact;
import com.unibell.clientsdatabase.dto.ClientContactByType;
import com.unibell.clientsdatabase.entity.Client;
import com.unibell.clientsdatabase.entity.Contact;
import com.unibell.clientsdatabase.exception.ClientNotFoundException;
import com.unibell.clientsdatabase.mapper.ContactMapper;
import com.unibell.clientsdatabase.repository.ContactRepository;
import com.unibell.clientsdatabase.service.ClientService;
import com.unibell.clientsdatabase.testData.TestData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestData.class)
class ContactServiceImplTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;
    @Mock
    private ClientService clientService;

    @InjectMocks
    private ContactServiceImpl contactService;

    private Contact email;
    private Contact phone;
    private Client client;

    @BeforeEach
    void init() {
        email = TestData.randomTestDataContactEMail();
        phone = TestData.randomTestDataContactPhone();
        client = TestData.randomTestDataClient();
    }

    @AfterEach
    void cleanup() {
        contactRepository.deleteAll();
    }

    @Test
    @DisplayName("Test adding a new contact - successful")
    void testAddNewContact() {
        when(clientService.findClientById(client.getId())).thenReturn(Optional.of(client));

        assertDoesNotThrow(() -> contactService.addNewContact(
                client.getId(), email.getType(), email.getTypeValue()));
    }

    @Test
    @DisplayName("Test adding a new contact - unsuccessful")
    void testAddNewContactUnsuccessful() {
        when(clientService.findClientById(client.getId())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> contactService.addNewContact(
                client.getId(), phone.getType(), phone.getTypeValue()));
    }

    @Test
    @DisplayName("Test getting of client's contact - successful")
    void testGetClientContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(email);
        contacts.add(phone);

        when(contactRepository.findAllByClientId(client.getId())).thenReturn(contacts);

        List<ClientContact> clientContacts = contactService.getClientContacts(client.getId());

        assertEquals(contacts.size(), clientContacts.size());
    }

    @Test
    @DisplayName("Test getting of client's contact - empty list")
    void testGetClientContactsEmptyList() {
        when(contactRepository.findAllByClientId(client.getId())).thenReturn(new ArrayList<>());

        List<ClientContact> clientContacts = contactService.getClientContacts(client.getId());

        assertTrue(clientContacts.isEmpty());
    }

    @Test
    @DisplayName("Test getting of client's contacts by type - successful")
    void testGetClientContactsByType() {
        List<Contact> emailContacts = new ArrayList<>();
        emailContacts.add(email);

        when(contactRepository.findAllByClientIdAndType(client.getId(), email.getType())).thenReturn(emailContacts);

        List<ClientContactByType> clientContactsByType = contactService.getClientContacts(client.getId(), email.getType());

        assertEquals(emailContacts.size(), clientContactsByType.size());
    }

    @Test
    @DisplayName("Test getting of client's contacts by type - empty list")
    void testGetClientContactsByTypeEmptyList() {
        when(contactRepository.findAllByClientIdAndType(client.getId(), email.getType())).thenReturn(new ArrayList<>());

        List<ClientContactByType> clientContactsByType = contactService.getClientContacts(client.getId(), email.getType());

        assertTrue(clientContactsByType.isEmpty());
    }
}