package com.unibell.clientsdatabase.controller;

import com.unibell.clientsdatabase.dto.ClientDTO;
import com.unibell.clientsdatabase.dto.ClientInfo;
import com.unibell.clientsdatabase.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing clients.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/clients")
@Tag(name = "Clients")
public class ClientController {

    private final ClientService clientService;

    /**
     * Adds a new client.
     *
     * @param dto the client data transfer object
     * @return a response entity with status code 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Add a new client")
    public ResponseEntity<Void> addNewClient(@RequestBody ClientDTO dto) {
        clientService.addNewClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Gets the client's info.
     *
     * @param clientId the client ID
     * @return a response entity with status code 200 (OK) and the client's info
     */
    @GetMapping("{clientId}")
    @Operation(summary = "Get client's info")
    public ResponseEntity<ClientInfo> getClientInfo(@PathVariable Long clientId) {
        return ResponseEntity.ok(clientService.getClientInfo(clientId));
    }

    /**
     * Gets all clients.
     *
     * @param pageNumber the page number
     * @param pageSize the page size
     * @return a response entity with status code 200 (OK) and the list of clients
     */
    @GetMapping
    @Operation(summary = "Get all clients")
    public ResponseEntity<List<ClientInfo>> getClients(
            @RequestParam(value = "page") Integer pageNumber,
            @RequestParam(value = "size") Integer pageSize) {
        return ResponseEntity.ok(clientService.getClients(pageNumber, pageSize));
    }
}
