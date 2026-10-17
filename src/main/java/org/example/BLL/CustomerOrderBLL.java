package org.example.BLL;

import org.example.BLL.Validators.QuantityValidator;
import org.example.BLL.Validators.Validator;
import org.example.DAO.BillDAO;
import org.example.DAO.CustomerOrderDAO;
import org.example.Model.Bill;
import org.example.Model.CustomerOrder;
import org.example.Model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class for managing {@link CustomerOrder} operations.
 * Coordinates interaction between Client, Product, and Bill services
 *
 * @author Bandila Briana
 * @version 1.0
 */

public class CustomerOrderBLL {
    private CustomerOrderDAO customerOrderDAO;
    private ClientBLL clientBll;
    private ProductBLL productBll;
    private BillBLL billBll;

    private List<Validator<CustomerOrder>> validators;

    /**
     * Instantiates a new CustomerOrderBLL service
     */
    public CustomerOrderBLL(){
        customerOrderDAO = new CustomerOrderDAO();
        clientBll = new ClientBLL();
        productBll = new ProductBLL();
        billBll = new BillBLL();

        validators = new ArrayList<>();
        validators.add(new QuantityValidator());
    }

    /**
     * Retrieves all customer orders
     *
     * @return A list of all existing customer orders.
     */
    public List<CustomerOrder> findAll(){
        return customerOrderDAO.findAll();
    }

    public CustomerOrder findById(int id){
        CustomerOrder customerOrder = customerOrderDAO.findById(id);
        if(customerOrder == null){
            throw new NoSuchElementException("Order with id " + id + " does not exist!");
        }

        return customerOrder;
    }

    /**
     * Retrieves an order associated with a specific client ID.
     *
     * @param clientId The identifier of the client.
     * @return The CustomerOrder object.
     * @throws NoSuchElementException If the client has no existing orders.
     */
    public CustomerOrder findByClientId(int clientId){
        CustomerOrder order = customerOrderDAO.findByClientId(clientId);
        if(order == null){
            throw new NoSuchElementException("Client has no order");
        }

        return order;
    }

    /**
     * Validates and processes a new customer order.
     * Checks client and product existence
     * Verifies warehouse stock availability, Updates
     * generates a {@link Bill}.
     *
     * @param customerOrder The order details to process.
     * @return The {@link CustomerOrder}
     * @throws IllegalStateException If the client already has an order, the product is already ordered,
     * or if there is insufficient warehouse stock.
     */
    public CustomerOrder insert(CustomerOrder customerOrder){
        validators.forEach(validator -> validator.validate(customerOrder));

        clientBll.findById(customerOrder.getId_client());

        if(customerOrderDAO.findByClientId(customerOrder.getId_client()) != null){
            throw new IllegalStateException("Client already has an order");
        }

        if(customerOrderDAO.findByProductId(customerOrder.getId_product()) != null){
            throw new IllegalStateException("Product has already been ordered");
        }

        Product product = productBll.findById(customerOrder.getId_product());
        if(product.getStock() <customerOrder.getQuantity())
        {
            throw new IllegalStateException("Insuficient stock! The product has " + product.getStock() + " stock!");
        }

        product.setStock(product.getStock()-customerOrder.getQuantity());
        productBll.update(product);

        CustomerOrder insertedOrder = customerOrderDAO.insert(customerOrder);

        double price = product.getPrice()*customerOrder.getQuantity();

        Bill newBill = new Bill(0,insertedOrder.getId(), insertedOrder.getId_client(), price, LocalDateTime.now());
        billBll.insert(newBill);

        return insertedOrder;
    }

    public CustomerOrder update(CustomerOrder customerOrder){
        validators.forEach(validator -> validator.validate(customerOrder));

        CustomerOrder oldOrder=this.findById(customerOrder.getId());

        clientBll.findById(customerOrder.getId_client());

        Product product = productBll.findById(customerOrder.getId_product());
        int quantityDifference = customerOrder.getQuantity()-oldOrder.getQuantity();

        if(quantityDifference>0){
            if(product.getStock()<quantityDifference){
                throw new IllegalStateException("Insuficient stock! The product has " + product.getStock() + " stock!");

            }
        }
        product.setStock(product.getStock()-quantityDifference);
        productBll.update(product);

        return customerOrderDAO.update(customerOrder);
    }

    public void delete(CustomerOrder customerOrder){
        Product product = productBll.findById(customerOrder.getId_product());
        product.setStock(product.getStock()+customerOrder.getQuantity());
        productBll.update(product);

        customerOrderDAO.delete(customerOrder);
    }
}
