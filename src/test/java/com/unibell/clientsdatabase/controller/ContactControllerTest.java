package com.unibell.clientsdatabase.controller;

import com.unibell.clientsdatabase.dto.ClientContact;
import com.unibell.clientsdatabase.dto.ClientContactByType;
import com.unibell.clientsdatabase.entity.Client;
import com.unibell.clientsdatabase.repository.ClientRepository;
import com.unibell.clientsdatabase.repository.ContactRepository;
import com.unibell.clientsdatabase.testData.TestData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ClientRepository clientRepository;

    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private Client client;

    @BeforeEach
    void setup() {
        client = TestData.randomTestDataClient();
    }

    @AfterEach
    void cleanup() {
        contactRepository.deleteAll();
        clientRepository.deleteAll();
    }


    @Test
    @DisplayName("Test adding a new contact - successful")
    void addContactToClient() {
        clientRepository.save(client);
        Long clientId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM clients", Long.class);

        ResponseEntity<Void> response = testRestTemplate.postForEntity("http://localhost:" + port + "/clients/" + clientId +"/contact?type=EMAIL&value=example@example.com", null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("Test adding a new contact - unsuccessful")
    void addContactToClientUnsuccessful() {
        long clientId = 1L;

        ResponseEntity<Void> response = testRestTemplate.postForEntity("http://localhost:" + port + "/clients/" + clientId +"/contact?type=EMAIL&value=example@example.com", null, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Test getting of client's contact - successful")
    void getClientContacts() {
        ResponseEntity<ClientContact[]> response = testRestTemplate.getForEntity("http://localhost:" + port + "/clients/1/contact", ClientContact[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Test getting of client's contacts by type - successful")
    void getClientContactsByType() {
        ResponseEntity<ClientContactByType[]> response = testRestTemplate.getForEntity("http://localhost:" + port + "/clients/1/contacts?type=EMAIL", ClientContactByType[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}