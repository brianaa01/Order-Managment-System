package org.example.Model;

/**
 * Represents a CustomerOrder entity
 * * @author Bandila Briana
 * @version 1.0
 */

public class CustomerOrder {
    private int id;
    private int id_client;
    private int id_product;
    private int quantity;

    /**
     * Default constructor (no parameters).
     */
    public CustomerOrder() {}

    /**
     * Constructor for creating a new order without an ID (auto-incremented).
     */
    public CustomerOrder(int clientId,int productId,int quantity) {

        this.id_client = clientId;
        this.id_product = productId;
        this.quantity = quantity;
    }

    /**
     * Constructor for reconstructing an existing order from the database.
     */
    public CustomerOrder(int id, int clientId,int productId,int quantity) {
        this.id = id;
        this.id_client = clientId;
        this.id_product = productId;
        this.quantity = quantity;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getId_client() {return id_client;}
    public void setId_client(int clientId) {this.id_client = clientId;}

    public int getId_product() {return id_product;}
    public void setId_product(int productId) {this.id_product = productId;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    @Override
    public String toString() {
        return "CustomerOrder{" + "id=" + id + ", clientId=" + id_client + " , productId=" + id_product + ", quantity=" + quantity + '}';
    }

}
