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

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    @Override
    public void addNewClient(ClientDTO dto) {
        log.info("new client {}", dto.getName());
        clientRepository.save(clientMapper.toEntityClient(dto));
    }

    @Override
    public ClientInfo getClientInfo(Long id) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if(clientOpt.isPresent()) {
            return clientMapper.toDtoClientInfo(clientOpt.get());
        } else {
            throw new ClientNotFoundException("Client not found with id: " + id);
        }
    }

    @Override
    public List<ClientInfo> getClients(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Client> clients = clientRepository.findAll(pageRequest).getContent();
        return clients.stream()
                .map(clientMapper::toDtoClientInfo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Client> findClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }
}
