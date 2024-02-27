package com.unibell.clientsdatabase.controller;

import com.unibell.clientsdatabase.dto.ClientDTO;
import com.unibell.clientsdatabase.dto.ClientInfo;
import com.unibell.clientsdatabase.entity.Client;
import com.unibell.clientsdatabase.repository.ClientRepository;
import com.unibell.clientsdatabase.testData.TestData;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ClientControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    ClientController clientController;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    private ClientDTO clientDTO;
    private Client client;


    @BeforeEach
    void setup() {
        clientDTO = TestData.randomTestDataClientDTO();
        client = TestData.randomTestDataClient();
    }

    @AfterEach
    void cleanup() {
        clientRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        Assertions.assertThat(clientController).isNotNull();
    }

    @Test
    @DisplayName("Test adding a new client - successful")
    public void testAddNewClient() {

        ResponseEntity<Void> response = testRestTemplate.postForEntity("/clients", clientDTO, Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Test adding a new client with empty name - unsuccessful")
    public void testAddNewClientEmptyNameUnsuccessful() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("");

        assertThrows(AssertionFailedError.class, () -> {
            ResponseEntity<String> response = testRestTemplate.exchange("/clients", HttpMethod.POST, new HttpEntity<>(clientDTO), String.class);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertTrue(Objects.requireNonNull(response.getBody()).contains("Validation failed"));
        });
    }

    @Test
    @DisplayName("Test getting client info - successful")
    public void testGetClientInfo() {

        clientRepository.save(client);
        Long clientId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM clients", Long.class);

        ResponseEntity<ClientInfo> response = testRestTemplate.getForEntity("/clients/{clientId}", ClientInfo.class, clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Test getting client info - unsuccessful")
    public void testGetClientInfoUnsuccessful() {
        Long clientId = 1L;
        try {
            ResponseEntity<ClientInfo> response = testRestTemplate.getForEntity("/clients/{clientId}", ClientInfo.class, clientId);

            assertNull(response.getBody());
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    @DisplayName("Test getting clients - successful")
    public void testGetClients() {
        clientRepository.save(client);
        Long clientId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM clients", Long.class);
        ClientInfo clientToSave = new ClientInfo();
        clientToSave.setName(client.getName());
        clientToSave.setId(clientId);
        Integer page = 1;
        Integer size = 10;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<ClientInfo>> response = testRestTemplate.exchange(
                "/clients?page={page}&size={size}",
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {
                }, page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Test getting clients - empty list")
    public void testGetClientsEmptyList() {
        Integer page = 1;
        Integer size = 10;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<ClientInfo>> response = testRestTemplate.exchange(
                "/clients?page={page}&size={size}",
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {
                }, page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}