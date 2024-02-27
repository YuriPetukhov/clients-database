package com.unibell.clientsdatabase.mapper;

import com.unibell.clientsdatabase.dto.ClientDTO;
import com.unibell.clientsdatabase.dto.ClientInfo;
import com.unibell.clientsdatabase.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntityClient(ClientDTO dto);

    ClientInfo toDtoClientInfo(Client client);
}
