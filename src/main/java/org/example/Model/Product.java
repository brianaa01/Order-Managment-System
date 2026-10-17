package org.example.Model;

/**
 * Represents a Product entity
 * * @author Bandila Briana
 * @version 1.0
 */
public class Product {
    private int id;
    private int stock;
    private String name;
    private double price;
    private String category;

    /**
     * Default constructor (no parameters).
     */
    public Product() {

    }

    /**
     * Constructor for creating a new product without an ID (auto-incremented).
     */
    public Product(int stock, String name, double price, String category) {
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * Constructor for reconstructing an existing product from the database.
     */
    public Product(int id, int stock, String name, double price, String category) {
        this.id = id;
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", stock=" + stock +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
