package com.unibell.clientsdatabase.repository;

import com.unibell.clientsdatabase.entity.Contact;
import com.unibell.clientsdatabase.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByClientId(Long clientId);

    List<Contact> findAllByClientIdAndType(Long clientId, ContactType type);

    Contact findByClientIdAndTypeAndValue(Long clientId, ContactType type, String value);
}
