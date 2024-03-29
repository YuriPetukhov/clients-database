package com.unibell.clientsdatabase.entity;

import com.unibell.clientsdatabase.enums.ContactType;
import com.unibell.clientsdatabase.validation.ValidContact;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidContact
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    private ContactType type;

    @JoinColumn(name = "type_value")
    private String typeValue;

}
