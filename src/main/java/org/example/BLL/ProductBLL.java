package org.example.BLL;

import org.example.BLL.Validators.PriceValidator;
import org.example.BLL.Validators.StockValidator;
import org.example.BLL.Validators.Validator;
import org.example.DAO.ProductDAO;
import org.example.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class for managing {@link Product} entities.
 *
 * @author Your Name
 * @version 1.0
 */

public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    /**
     * Instantiates a new ProductBLL service
     */
    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new PriceValidator());
        validators.add(new StockValidator());

        productDAO = new ProductDAO();
    }

    /**
     * Retrieves a complete list of all products
     *
     * @return A list containing all existing products.
     */
    public List<Product> findAll() {
        return productDAO.findAll();

    }

    /**
     * Finds and retrieves a product by its unique ID.
     *
     * @param id The identifier of the product being searched
     * @return The {@link Product} object.
     * @throws NoSuchElementException If no product with the specified ID exists in the database.
     */
    public Product findById(int id) {
        Product p = productDAO.findById(id);
        if(p==null){
            throw new NoSuchElementException("Product with id="+id+" does not exist");
        }

        return p;
    }

    public Product findByName(String name) {
        Product p = productDAO.findByName(name);
        if(p==null){
            throw new NoSuchElementException("Product with name="+name+" does not exist");
        }

        return p;
    }

    /**
     * Validates and inserts a product in the database
     *
     * @param p The {@link Product} object
     * @return The inserted product object
     */
    public Product insert(Product p) {
        validators.forEach((v)->v.validate(p));
        return productDAO.insert(p);
    }

    /**
     * Validates and updates an existing product
     *
     * @param p The {@link Product} object
     * @return The updated product object, or null if the operation fails.
     */
    public Product update(Product p) {
        validators.forEach((v)->v.validate(p));
        return productDAO.update(p);
    }

    /**
     * Deletes a specific product from the database.
     *
     * @param p The {@link Product} object to be removed.
     */
    public void delete(Product p) {
        productDAO.delete(p);
    }
}
