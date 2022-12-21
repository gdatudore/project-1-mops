package org.example.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.example.hello.model.Client;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

    public Client findByName(String name);

    public Client findByEmail(String email);
}