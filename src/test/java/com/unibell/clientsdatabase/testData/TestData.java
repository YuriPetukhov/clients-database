package com.unibell.clientsdatabase.testData;

import com.github.javafaker.Faker;
import com.unibell.clientsdatabase.dto.ClientDTO;
import com.unibell.clientsdatabase.dto.ClientInfo;
import com.unibell.clientsdatabase.entity.Client;
import com.unibell.clientsdatabase.entity.Contact;
import com.unibell.clientsdatabase.enums.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestData {

    public static ClientDTO randomTestDataClientDTO() {
        ClientDTO clientDTO = new ClientDTO();
        Faker faker = new Faker();
        clientDTO.setName(faker.name().name());
        return clientDTO;
    }

    public static ClientInfo randomTestDataClientInfo() {
        ClientInfo clientInfo = new ClientInfo();
        Faker faker = new Faker();
        clientInfo.setName(faker.name().name());
        clientInfo.setId(faker.number().randomNumber());
        return clientInfo;
    }

    public static Client randomTestDataClient() {
        Client client = new Client();
        Faker faker = new Faker();
        client.setName(faker.name().name());
        client.setId(faker.number().randomNumber());
        return client;
    }

    public static Contact randomTestDataContactEMail() {
        Contact contact = new Contact();
        Faker faker = new Faker();
        contact.setType(ContactType.EMAIL);
        contact.setTypeValue(faker.phoneNumber().phoneNumber());
        contact.setId(faker.number().randomNumber());
        return contact;
    }

    public static Contact randomTestDataContactPhone() {
        Contact contact = new Contact();
        Faker faker = new Faker();
        contact.setType(ContactType.PHONE);
        contact.setTypeValue(faker.phoneNumber().phoneNumber());
        contact.setId(faker.number().randomNumber());
        return contact;
    }

}