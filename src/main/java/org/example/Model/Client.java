package org.example.Model;

/**
 * Represents a Client entity
 * * @author Bandila Briana
 * @version 1.0
 */

public class Client {
    private int id;
    private String name;
    private String email;
    private String adress;
    private String phone;

    /**
     * Default constructor (no parameters).
     */
    public Client()
    {

    }

    /**
     * Constructor for creating a new client (without an ID).
     * Typically used before inserting the client into the database,
     * where the ID is auto-incremented.
     *
     * @param name        The full name of the client.
     * @param email       The email address of the client.
     * @param address     The physical address of the client.
     * @param phoneNumber The contact phone number of the client.
     */
    public Client(String name, String email, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.adress = address;
        this.phone = phoneNumber;
    }

    /**
     * Full constructor for creating a client with an existing ID.
     *
     * @param id          The unique identifier of the client.
     * @param name        The full name of the client.
     * @param email       The email address of the client.
     * @param address     The physical address of the client.
     * @param phoneNumber The contact phone number of the client.
     */
    public Client(int id, String name, String email, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.adress = address;
        this.phone = phoneNumber;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getAdress() {return adress;}
    public void setAdress(String address) {this.adress = address;}

    public String getPhone() {return phone;}
    public void setPhone(String phoneNumber) {this.phone = phoneNumber;}

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + adress + '\'' +
                ", phoneNumber='" + phone + '\'' +
                '}';
    }


}
