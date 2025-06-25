package com.example.webapp.repository;

import com.example.webapp.models.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Clients, Integer> {

    public Clients findByEmail(String email);
}
