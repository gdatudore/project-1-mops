package org.example.hello.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.hello.exception.NotFoundException;
import org.example.hello.model.Client;
import org.example.hello.repository.ClientRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClientService {

    private final ClientRepository repository;

    @Autowired
    ClientService(ClientRepository clientRepository) {
        this.repository = clientRepository;
    }

    public Client getClientById(String clientId) {
        Optional<Client> client = repository.findById(clientId);
        if (client.isPresent()) {
            return client.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client createClient(Client client) {
        return repository.save(client);
    }

    public Client updateClient(String clientId, Client request) {
        Optional<Client> optionalClient = repository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");
        }

        Client client = optionalClient.get();
        client.setName(request.name);
        client.setEmail(request.email);
        client.setPhoneNumber(request.phoneNumber);
        client.setAddress(request.address);

        return repository.save(client);
    }

    public void deleteClientById(String clientId) {
        repository.deleteById(clientId);
    }
}