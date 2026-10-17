package org.example.DAO;


import org.example.Connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A generic Data Access Object (DAO) implementation that automates CRUD operations
 * for any entity type using Java Reflection and JDBC.
 *
 * @param <T> The entity type mapped by this DAO.
 * @author Bandila Briana
 * @version 1.0
 */


public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Instantiates a new Abstract DAO and uses reflection to determine the actual
     * class type of the generic parameter
     */

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Helper method to generate a standard SELECT query filtered by a specific field.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Retrieves all records from the corresponding database table.
     *
     * @return A list of all entity objects, or null if an error occurs.
     */
    public List<T> findAll() {
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            String query = "SELECT * FROM " + type.getSimpleName();
            try{
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();

                return createObjects(resultSet);
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll" + e.getMessage());
            }finally{
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }
        return null;
    }


    /**
     * Finds a single database record by its unique identifier (ID).
     *
     * @param id The identifier of the record
     * @return The entity object, or null if not found.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> list = createObjects(resultSet);
            if (list==null || list.isEmpty()) {
                return null;
            }

            return list.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Reconstructs a list of entity objects
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Inserts a new record into the database table
     * @param t The entity object containing data to insert.
     * @return The updated entity object with its assigned ID, or null if it fails.
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        ResultSet rs = null;

        Field[] allFields = type.getDeclaredFields();

        for(int i = 0; i < allFields.length; i++){
            if(allFields[i].getName().equals("id")){ continue; }
            fields.append(allFields[i].getName());
            values.append("?");
            if(i < allFields.length - 1){
                fields.append(", ");
                values.append(", ");
            }
        }

        String query = "INSERT INTO " + type.getSimpleName() + " (" + fields.toString() + ") VALUES (" + values.toString() + ")";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int index=1;
            for(Field field : type.getDeclaredFields()){
                if(field.getName().equals("id")){ continue; }
                field.setAccessible(true);
                statement.setObject(index++,field.get(t));
            }

            statement.executeUpdate();

            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);

                Field idField = type.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(t, generatedId);
            }
            return t;
        }catch(SQLException | IllegalAccessException | NoSuchFieldException e)
        {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert" + e.getMessage());
        }finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }


        return null;
    }

    /**
     * Updates an existing database record
     *
     * @param t The entity object
     * @return The updated entity object, or null if the operation fails.
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder query = new StringBuilder();
        query.append("UPDATE " + type.getSimpleName() + " SET ");

        Field[] allFields = type.getDeclaredFields();
        Object idValue = null;

        try{
            for(int i=0; i < allFields.length; i++){
                Field field = allFields[i];
                field.setAccessible(true);
                if(field.getName().equals("id")){
                    idValue = field.get(t);
                    continue;
                }

                query.append(field.getName() + " = ?");

                if(i < allFields.length - 1){
                    query.append(", ");
                }
            }

            query.append(" WHERE id = ?");

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());

            int index=1;
            for(Field field : allFields){
                if(field.getName().equals("id")){ continue; }

                field.setAccessible(true);
                statement.setObject(index++, field.get(t));
            }

            statement.setObject(index,idValue);

            statement.executeUpdate();
            return t;
        }catch(SQLException | IllegalAccessException e)
        {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update" + e.getMessage());
        }finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }



        return null;
    }

    /**
     * Deletes a record from the database table based on the entity's ID field.
     *
     * @param t The entity object representing the row to be deleted.
     */
    public void delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field idField =  type.getDeclaredField("id");
            idField.setAccessible(true);
            int idValue=(int) idField.get(t);

            statement.setInt(1,idValue);

            statement.executeUpdate();


        }catch(SQLException | IllegalAccessException | NoSuchFieldException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete" + e.getMessage());
        }finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);

        }
    }
}

