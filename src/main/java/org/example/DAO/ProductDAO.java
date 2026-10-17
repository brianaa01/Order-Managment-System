package org.example.DAO;

import org.example.Connection.ConnectionFactory;
import org.example.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Data Access Object (DAO) for the {@link Product} entity.
 * Extends {@link AbstractDAO} to inherit generic CRUD
 *
 * @author Bandila Briana
 * @version 1.0
 */

public class ProductDAO extends AbstractDAO<Product>{

    /**
     * Finds and retrieves a product from the database using its unique name.
     *
     * @param name The name of the product
     * @return The product object, or null if no matching record exists.
     */
    public Product findByName(String name){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Product WHERE name=?";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1,name);
            resultSet = statement.executeQuery();

            List<Product> result = createObjects(resultSet);
            if(result!=null && !result.isEmpty()){
                return result.get(0);
            }

        }catch(SQLException e)
        {
            LOGGER.log(Level.WARNING, "ProductDAO:findByName " + e.getMessage());
        }finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }
}
