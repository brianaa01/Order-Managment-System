package org.example.DAO;

import org.example.Connection.ConnectionFactory;
import org.example.Model.CustomerOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Data Access Object (DAO) for the {@link CustomerOrder} entity.
 * Extends {@link AbstractDAO} to inherit generic CRUD operations
 *
 * @author Bandila Briana
 * @version 1.0
 */

public class CustomerOrderDAO extends AbstractDAO<CustomerOrder> {

    /**
     * Finds and retrieves the first order associated with a client ID.
     *
     * @param id_client The unique identifier of the client.
     * @return The order object, or null if no matching record exists.
     */
    public CustomerOrder findByClientId(int id_client){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM CustomerOrder WHERE id_client=?";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1,id_client);
            resultSet = statement.executeQuery();

            List<CustomerOrder> list = createObjects(resultSet);
            if(list != null && !list.isEmpty()) {
                return list.get(0);
            }

        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "CustomerOrderDAO:findByClientId " + e.getMessage());
        }finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
            ConnectionFactory.close(resultSet);
        }

        return null;
    }

    /**
     * Finds and retrieves the first order associated with a product ID.
     *
     * @param id_product The unique identifier of the product.
     * @return The product object, or null if no matching record exists.
     */
    public CustomerOrder findByProductId(int id_product){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM CustomerOrder WHERE id_product=?";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1,id_product);
            resultSet = statement.executeQuery();

            List<CustomerOrder> list = createObjects(resultSet);
            if(list != null && !list.isEmpty()) {
                return list.get(0);
            }

        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "CustomerOrderDAO:findByProductId " + e.getMessage());
        }finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
            ConnectionFactory.close(resultSet);
        }

        return null;
    }
}
