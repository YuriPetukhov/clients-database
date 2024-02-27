package com.unibell.clientsdatabase.controller;

import com.unibell.clientsdatabase.dto.ClientContact;
import com.unibell.clientsdatabase.dto.ClientContactByType;
import com.unibell.clientsdatabase.enums.ContactType;
import com.unibell.clientsdatabase.service.ContactService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/contacts")
@Tag(name = "Contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/add-contact/{clientId}")
    @Operation(summary = "Add a new contact")
    public ResponseEntity<Void> addContactToClient(
            @PathVariable Long clientId,
            @ApiParam(value = "Type of contact: EMAIL or PHONE", example = "EMAIL")
            @RequestParam ContactType type,
            @ApiParam(value = "Value of the contact", example = "example@example.com or +7(901) 234-56-78", defaultValue = "example@example.com")
            @RequestParam String value) {

        contactService.addNewContact(clientId, type, value);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("{clientId}/all")
    @Operation(summary = "Get client's contacts")
    public ResponseEntity<List<ClientContact>> getClientContacts(
            @PathVariable Long clientId) {
        return ResponseEntity.ok(contactService.getClientContacts(clientId));
    }
    @GetMapping("{clientId}/type")
    @Operation(summary = "Get client's contacts by type")
    public ResponseEntity<List<ClientContactByType>> getClientContacts(
            @PathVariable Long clientId,
            @RequestParam ContactType type) {
        return ResponseEntity.ok(contactService.getClientContacts(clientId, type));
    }
}
