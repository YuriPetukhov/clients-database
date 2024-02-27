package com.unibell.clientsdatabase.service;

import com.unibell.clientsdatabase.dto.ClientDTO;
import com.unibell.clientsdatabase.dto.ClientInfo;
import com.unibell.clientsdatabase.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    void addNewClient(ClientDTO dto);

    ClientInfo getClientInfo(Long id);

    List<ClientInfo> getClients(Integer pageNumber, Integer pageSize);

    Optional<Client> findClientById(Long clientId);
}
