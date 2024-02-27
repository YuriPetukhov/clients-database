package com.unibell.clientsdatabase.repository;

import com.unibell.clientsdatabase.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
