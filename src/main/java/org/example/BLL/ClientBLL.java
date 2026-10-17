package org.example.BLL;

import org.example.BLL.Validators.EmailValidator;
import org.example.BLL.Validators.NameValidator;
import org.example.BLL.Validators.PhoneValidator;
import org.example.BLL.Validators.Validator;
import org.example.DAO.ClientDAO;
import org.example.Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class for managing {@link Client} entities.
 * Intermediary layer between the presentation layer and DAO
 *
 * @author Bandila Briana
 * @version 1.0
 */

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    /**
     * Instantiates a new ClientBLL service, registers necessary domain validators
     * (Email, Phone, and Name validators), and initializes the Client DAO.
     */
    public ClientBLL() {
        this.validators = new ArrayList<Validator<Client>>();
        this.validators.add(new EmailValidator());
        this.validators.add(new PhoneValidator());
        this.validators.add(new NameValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * Retrieves a specific client from the database by their unique ID.
     *
     * @param id The identifier of the client to find.
     * @return The {@link Client} object.
     * @throws NoSuchElementException If no client with the specified ID exists.
     */
    public Client findById(int id) {
        Client client = clientDAO.findById(id);
        if(client == null){
            throw new NoSuchElementException("Client with id " + id + " not found");
        }

        return client;
    }

    /**
     * Validates a client's data and inserts them into the database if the email is unique.
     *
     * @param client The {@link Client} object containing the new data to insert.
     * @return The successfully inserted client object with its generated primary key.
     * @throws IllegalStateException If a client with the same email already exists.
     */
    public Client insert(Client client) {
        validators.forEach(validator -> validator.validate(client));

        Client existingClient = clientDAO.findByEmail(client.getEmail());
        if(existingClient != null){
            throw new IllegalStateException("Client with email " + client.getEmail() + " already exists");
        }

        return clientDAO.insert(client);
    }

    /**
     * Validates and updates an existing client's information in the database.
     *
     * @param client The {@link Client} object containing the updated data.
     * @return The updated client object, or null if the operation fails.
     */
    public Client update(Client client) {
        validators.forEach(validator -> validator.validate(client));

        return clientDAO.update(client);
    }

    /**
     * Deletes a client from the database.
     *
     * @param client The {@link Client} object to be removed.
     */
    public void delete(Client client) {
        clientDAO.delete(client);
    }

    /**
     * Retrieves a complete list of all clients
     *
     * @return A list containing all existing clients.
     */
    public List<Client> findAll() {
        return clientDAO.findAll();
    }

    /**
     * Retrieves a client from the database using their unique email address.
     *
     * @param email The unique email address to search
     * @return The {@link Client} object.
     * @throws NoSuchElementException If no client with the specified email exists.
     */
    public Client findByEmail(String email) {
        Client client = clientDAO.findByEmail(email);
        if(client == null){
            throw new NoSuchElementException("Client with email " + email + " not found");
        }

        return client;
    }
}

