package com.unibell.clientsdatabase.service.impl;

import com.unibell.clientsdatabase.dto.ClientDTO;
import com.unibell.clientsdatabase.dto.ClientInfo;
import com.unibell.clientsdatabase.entity.Client;
import com.unibell.clientsdatabase.exception.ClientNotFoundException;
import com.unibell.clientsdatabase.mapper.ClientMapper;
import com.unibell.clientsdatabase.repository.ClientRepository;
import com.unibell.clientsdatabase.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing clients.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    /**
     * Adds a new client.
     *
     * @param dto the client data transfer object
     */
    @Override
    public void addNewClient(ClientDTO dto) {
        log.info("new client {}", dto.getName());
        clientRepository.save(clientMapper.toEntityClient(dto));
    }

    /**
     * Gets client info by ID.
     *
     * @param id the client ID
     * @return the client info data transfer object
     * @throws ClientNotFoundException if the client is not found
     */
    @Override
    public ClientInfo getClientInfo(Long id) {
        log.info("information about client id {}", id);
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isPresent()) {
            return clientMapper.toDtoClientInfo(clientOpt.get());
        } else {
            throw new ClientNotFoundException("Client not found with id: " + id);
        }
    }

    /**
     * Gets all clients.
     *
     * @param pageNumber the page number
     * @param pageSize the page size
     * @return the list of client info data transfer objects
     */
    @Override
    public List<ClientInfo> getClients(Integer pageNumber, Integer pageSize) {
        log.info("list of clients");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Client> clients = clientRepository.findAll(pageRequest).getContent();
        return clients.stream()
                .map(clientMapper::toDtoClientInfo)
                .collect(Collectors.toList());
    }

    /**
     * Finds a client by ID.
     *
     * @param clientId the client ID
     * @return the optional client
     */
    @Override
    public Optional<Client> findClientById(Long clientId) {
        log.info("try to find client by id {}", clientId);
        return clientRepository.findById(clientId);
    }
}
