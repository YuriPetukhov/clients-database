package com.unibell.clientsdatabase.service.impl;

import com.unibell.clientsdatabase.dto.ClientDTO;
import com.unibell.clientsdatabase.dto.ClientInfo;
import com.unibell.clientsdatabase.entity.Client;
import com.unibell.clientsdatabase.exception.ClientNotFoundException;
import com.unibell.clientsdatabase.mapper.ClientMapper;
import com.unibell.clientsdatabase.repository.ClientRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestData.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientDTO clientDTO;
    private ClientInfo clientInfo1;
    private ClientInfo clientInfo2;
    private Client client;

    @BeforeEach
    void init() {
        clientDTO = TestData.randomTestDataClientDTO();
        clientInfo1 = TestData.randomTestDataClientInfo();
        clientInfo2 = TestData.randomTestDataClientInfo();
        client = TestData.randomTestDataClient();
    }

    @AfterEach
    void cleanup() {
        clientRepository.deleteAll();
    }

    @Test
    @DisplayName("Test adding a new client - successful")
    void addNewClient() {
        Client client = new Client();

        when(clientMapper.toEntityClient(clientDTO)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);

        clientService.addNewClient(clientDTO);

        verify(clientRepository, times(1)).save(client);
    }

    @Test
    @DisplayName("Test adding a new client - unsuccessful")
    void addNewClientUnsuccessful() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("");
        clientService.addNewClient(clientDTO);

        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Test getting client info - successful")
    void getClientInfo() {

        Client client = new Client();
        client.setName(clientInfo1.getName());
        client.setId(clientInfo1.getId());

        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(clientMapper.toDtoClientInfo(any(Client.class))).thenReturn(clientInfo1);

        ClientInfo clientInfo = clientService.getClientInfo(client.getId());

        assertEquals(client.getName(), clientInfo.getName());
    }

    @Test
    @DisplayName("Test getting client info - unsuccessful")
    void getClientInfoUnsuccessful() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientInfo(1L));
    }

    @Test
    @DisplayName("Test getting clients - successful")
    void getClients() {
        Client client1 = new Client();
        client1.setName(clientInfo1.getName());
        client1.setId(clientInfo1.getId());

        Client client2 = new Client();
        client2.setName(clientInfo2.getName());
        client2.setId(clientInfo2.getId());

        List<Client> testClients = new ArrayList<>();
        testClients.add(client1);
        testClients.add(client2);

        Page<Client> page = new PageImpl<>(testClients);
        when(clientRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(clientMapper.toDtoClientInfo(client1)).thenReturn(clientInfo1);
        when(clientMapper.toDtoClientInfo(client2)).thenReturn(clientInfo2);

        List<ClientInfo> clientInfos = clientService.getClients(1, 10);

        assertEquals(2, clientInfos.size());
        assertEquals(client1.getName(), clientInfos.get(0).getName());
        assertEquals(client2.getName(), clientInfos.get(1).getName());
    }

    @Test
    @DisplayName("Test getting clients with empty database")
    void testGetClientsEmptyDatabase() {
        List<Client> emptyList = Collections.emptyList();
        Page<Client> emptyPage = new PageImpl<>(emptyList);

        when(clientRepository.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        List<ClientInfo> clientInfos = clientService.getClients(1, 10);

        assertTrue(clientInfos.isEmpty());
    }

    @Test
    @DisplayName("Test finding a client by ID - successful")
    void testFindClientById() {

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.findClientById(client.getId());

        assertTrue(result.isPresent());
        assertEquals(client, result.get());
    }

    @Test
    @DisplayName("Test finding a client by ID - unsuccessful")
    void testFindClientById_unsuccessful() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());

        Optional<Client> result = clientService.findClientById(client.getId());

        assertTrue(result.isEmpty());
    }
}