package org.example.DAO;

import org.example.Connection.ConnectionFactory;
import org.example.Model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Data Access Object (DAO) implemented for the {@link Bill} record
 *
 * @author Bandila Briana
 * @version 1.0
 */

public class BillDAO {

    /**
     * Inserts a new record into the Log database table.
     *
     * @param bill The immutable Bill record
     */
    public void insert(Bill bill){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO Log (id_order, id_client, amount, issueDate) VALUES (?, ?, ?, ?)";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1,bill.id_order());
            statement.setInt(2,bill.id_client());
            statement.setDouble(3,bill.amount());
            statement.setTimestamp(4, Timestamp.valueOf(bill.issueDate()));

            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);

        }
    }

    /**
     * Retrieves all transaction logs from the Log database table.
     *
     * @return A list of all existing Bill records recovered from the database.
     */
    public List<Bill> findAll(){
        List<Bill> bills = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Log";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Bill bill = new Bill(resultSet.getInt("id"),
                        resultSet.getInt("id_order"),
                        resultSet.getInt("id_client"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("issueDate").toLocalDateTime());
                bills.add(bill);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(resultSet);
        }
        return bills;
    }
}
