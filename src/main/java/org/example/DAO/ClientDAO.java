package org.example.DAO;

import org.example.Connection.ConnectionFactory;
import org.example.Model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Data Access Object (DAO) for the {@link Client} entity.
 * Extends {@link AbstractDAO} to inherit generic CRUD operations
 *
 * @author Bandila Briana
 * @version 1.0
 */

public class ClientDAO extends AbstractDAO<Client> {

    /**
     * Finds and retrieves a specific client from the database using their email address.
     *
     * @param email The unique email address of the client
     * @return The found client object, or null if no matching record exists.
     */
    public Client findByEmail(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM Client WHERE email = ?";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1,email);
            resultSet = statement.executeQuery();

            List<Client> list = createObjects(resultSet);
            if(list!=null && !list.isEmpty()){
                return list.get(0);
            }
        }catch(SQLException e)
        {
            LOGGER.log(Level.WARNING, "ClientDAO:findByEmail " + e.getMessage());
        }finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;

    }
}
