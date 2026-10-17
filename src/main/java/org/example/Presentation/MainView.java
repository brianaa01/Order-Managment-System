package org.example.Presentation;

import org.example.BLL.BillBLL;
import org.example.BLL.ClientBLL;
import org.example.BLL.CustomerOrderBLL;
import org.example.BLL.ProductBLL;
import org.example.Model.Bill;
import org.example.Model.Client;
import org.example.Model.CustomerOrder;
import org.example.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import java.lang.reflect.Field;

public class MainView extends JFrame {
    private JPanel panel1;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel clientPanel;
    private JPanel productPanel;
    private JPanel orderPanel;
    private JTable clientTable;
    private JTable productTable;
    private JTable orderTable;
    private JPanel billPanel;
    private JTable billTable;

    private JTextField clIdField, clNameField, clEmailField, clAddressField, clPhoneField;
    private JButton addClientBtn, updateClientBtn, deleteClientBtn;

    private JTextField prIdField, prNameField, prStockField, prPriceField, prCategoryField;
    private JButton addProductBtn, updateProductBtn, deleteProductBtn;

    private JTextField ordIdField, ordClientIdField, ordProductIdField, ordQuantityField;
    private JButton placeOrderBtn, deleteOrderBtn, updateOrderBtn;

    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private CustomerOrderBLL orderBLL;
    private BillBLL billBLL;


    public MainView() {
        setTitle("Order Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(950,700);
        setLocationRelativeTo(null);

        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderBLL = new CustomerOrderBLL();
        billBLL = new BillBLL();

        buildClientControls();
        buildProductControls();
        buildOrderControls();

        setupActions();

        refreshAllTables();
    }

    private void buildClientControls(){

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Client Information"));

        clIdField = new JTextField(); clIdField.setEditable(false);
        clNameField = new JTextField();
        clEmailField = new JTextField();
        clAddressField = new JTextField();
        clPhoneField = new JTextField();

        formPanel.add(new JLabel(" ID (Auto)")); formPanel.add(clIdField);
        formPanel.add(new JLabel(" Name:")); formPanel.add(clNameField);
        formPanel.add(new JLabel(" Email:")); formPanel.add(clEmailField);
        formPanel.add(new JLabel(" Address:")); formPanel.add(clAddressField);
        formPanel.add(new JLabel(" Phone:")); formPanel.add(clPhoneField);


        JPanel btnPanel = new JPanel(new FlowLayout());
        addClientBtn = new JButton("Add Client");
        updateClientBtn = new JButton("Update Client");
        deleteClientBtn = new JButton("Delete Client");
        btnPanel.add(addClientBtn); btnPanel.add(updateClientBtn); btnPanel.add(deleteClientBtn);


        JPanel bottomLayout = new JPanel(new BorderLayout());
        bottomLayout.add(formPanel, BorderLayout.CENTER);
        bottomLayout.add(btnPanel, BorderLayout.SOUTH);
        clientPanel.add(bottomLayout, BorderLayout.SOUTH);

        clientTable.getSelectionModel().addListSelectionListener(e -> {
            int row = clientTable.getSelectedRow();
            if (row != -1) {
                String idStr = clientTable.getValueAt(row, 0).toString();
                clIdField.setText(idStr);
                clNameField.setText(clientTable.getValueAt(row, 1).toString());
                clEmailField.setText(clientTable.getValueAt(row, 2).toString());
                clAddressField.setText(clientTable.getValueAt(row, 3).toString());
                clPhoneField.setText(clientTable.getValueAt(row, 4).toString());

                ordClientIdField.setText(idStr);
            }
        });
    }

    private void buildProductControls(){

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Product Information"));

        prIdField = new JTextField(); prIdField.setEditable(false);
        prNameField = new JTextField();
        prStockField = new JTextField();
        prPriceField = new JTextField();
        prCategoryField = new JTextField();

        formPanel.add(new JLabel(" ID (Auto):")); formPanel.add(prIdField);
        formPanel.add(new JLabel(" Name:")); formPanel.add(prNameField);
        formPanel.add(new JLabel(" Stock:")); formPanel.add(prStockField);
        formPanel.add(new JLabel(" Price:")); formPanel.add(prPriceField);
        formPanel.add(new JLabel(" Category:")); formPanel.add(prCategoryField);

        JPanel btnPanel = new JPanel(new FlowLayout());
        addProductBtn = new JButton("Add Product");
        updateProductBtn = new JButton("Update Product");
        deleteProductBtn = new JButton("Delete Product");
        btnPanel.add(addProductBtn); btnPanel.add(updateProductBtn); btnPanel.add(deleteProductBtn);

        JPanel bottomLayout = new JPanel(new BorderLayout());
        bottomLayout.add(formPanel, BorderLayout.CENTER);
        bottomLayout.add(btnPanel, BorderLayout.SOUTH);
        productPanel.add(bottomLayout, BorderLayout.SOUTH);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row != -1) {
                String idStr = productTable.getValueAt(row, 0).toString();
                prIdField.setText(idStr);
                prStockField.setText(productTable.getValueAt(row, 1).toString());
                prNameField.setText(productTable.getValueAt(row, 2).toString());
                prPriceField.setText(productTable.getValueAt(row, 3).toString());
                prCategoryField.setText(productTable.getValueAt(row, 4).toString());

                ordProductIdField.setText(idStr);
            }
        });
    }

    private void buildOrderControls() {

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Place Order Form"));

        ordIdField = new JTextField(); ordIdField.setEditable(false);
        ordClientIdField = new JTextField(); ordClientIdField.setEditable(false);
        ordProductIdField = new JTextField(); ordProductIdField.setEditable(false);
        ordQuantityField = new JTextField();

        formPanel.add(new JLabel(" Order ID (Auto):")); formPanel.add(ordIdField);
        formPanel.add(new JLabel(" Client ID:")); formPanel.add(ordClientIdField);
        formPanel.add(new JLabel(" Product ID:")); formPanel.add(ordProductIdField);
        formPanel.add(new JLabel(" Quantity:")); formPanel.add(ordQuantityField);

        JPanel btnPanel = new JPanel(new FlowLayout());
        placeOrderBtn = new JButton("Place Order");
        updateOrderBtn = new JButton("Update Order");
        deleteOrderBtn = new JButton("Delete Order");
        btnPanel.add(placeOrderBtn); btnPanel.add(updateOrderBtn); btnPanel.add(deleteOrderBtn);

        JPanel bottomLayout = new JPanel(new BorderLayout());
        bottomLayout.add(formPanel, BorderLayout.CENTER);
        bottomLayout.add(btnPanel, BorderLayout.SOUTH);
        orderPanel.add(bottomLayout, BorderLayout.SOUTH);

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            int row = orderTable.getSelectedRow();
            if (row != -1) {
                ordIdField.setText(orderTable.getValueAt(row, 0).toString());
                ordClientIdField.setText(orderTable.getValueAt(row, 1).toString());
                ordProductIdField.setText(orderTable.getValueAt(row, 2).toString());
                ordQuantityField.setText(orderTable.getValueAt(row, 3).toString());
            }
        });
    }

    private void setupActions()
    {
        addClientBtn.addActionListener(e -> {
            try{
                Client client = new Client(clNameField.getText(),clEmailField.getText(),clAddressField.getText(),clPhoneField.getText());
                clientBLL.insert(client);
                JOptionPane.showMessageDialog(this, "Client Added!");
                refreshAllTables();
                clearClientFields();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        updateClientBtn.addActionListener(e -> {
            try{
                if(clIdField.getText().isEmpty())
                {
                    throw new Exception("Please select a client from the table first!");
                }

                Client client = new Client(Integer.parseInt(clIdField.getText()),clNameField.getText(),clEmailField.getText(),clAddressField.getText(),clPhoneField.getText());
                clientBLL.update(client);
                JOptionPane.showMessageDialog(this, "Client Updated!");
                refreshAllTables();
                clearClientFields();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

        });

        deleteClientBtn.addActionListener(e -> {
            try{
                Client client = clientBLL.findById(Integer.parseInt(clIdField.getText()));
                clientBLL.delete(client);
                JOptionPane.showMessageDialog(this, "Client Deleted!");
                refreshAllTables();
                clearClientFields();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        addProductBtn.addActionListener(e -> {
            try {
                Product p = new Product(Integer.parseInt(prStockField.getText()), prNameField.getText(), Double.parseDouble(prPriceField.getText()), prCategoryField.getText());
                productBLL.insert(p);
                JOptionPane.showMessageDialog(this, "Product added successfully!");
                refreshAllTables();
                clearProductFields();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        updateProductBtn.addActionListener(e -> {
            try {
                if(prIdField.getText().isEmpty())
                {
                    throw new Exception("Please select a product from the table first!");
                }

                Product p = new Product(Integer.parseInt(prIdField.getText()), Integer.parseInt(prStockField.getText()), prNameField.getText(), Double.parseDouble(prPriceField.getText()), prCategoryField.getText());
                productBLL.update(p);
                JOptionPane.showMessageDialog(this, "Product updated successfully!");
                refreshAllTables();
                clearProductFields();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        deleteProductBtn.addActionListener(e -> {
            try {
                Product p = productBLL.findById(Integer.parseInt(prIdField.getText()));
                productBLL.delete(p);
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                refreshAllTables();
                clearProductFields();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        placeOrderBtn.addActionListener(e -> {
            try {
                CustomerOrder order = new CustomerOrder(
                        Integer.parseInt(ordClientIdField.getText()),
                        Integer.parseInt(ordProductIdField.getText()),
                        Integer.parseInt(ordQuantityField.getText())
                );
                orderBLL.insert(order);
                JOptionPane.showMessageDialog(this, "Order placed! Stock reduced.");
                refreshAllTables();
                clearOrderFields();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Order Blocked", JOptionPane.WARNING_MESSAGE); }
        });

        updateOrderBtn.addActionListener(e -> {
            try{
                if(ordIdField.getText().isEmpty()){
                    throw new Exception("Please select an order from the table first!");
                }
                CustomerOrder updatedOrder = new CustomerOrder(Integer.parseInt(ordIdField.getText()),Integer.parseInt(ordClientIdField.getText()),Integer.parseInt(ordProductIdField.getText()),Integer.parseInt(ordQuantityField.getText()));
                orderBLL.update(updatedOrder);
                JOptionPane.showMessageDialog(this, "Order and stock updated successfully!");
                refreshAllTables();
                clearOrderFields();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteOrderBtn.addActionListener(e -> {
            try {
                CustomerOrder o = orderBLL.findById(Integer.parseInt(ordIdField.getText()));
                orderBLL.delete(o);
                JOptionPane.showMessageDialog(this, "Order deleted!");
                refreshAllTables();
                clearOrderFields();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });
    }

    private void refreshAllTables(){
        List<Client> clients = clientBLL.findAll();
        if(clients!=null){
            clientTable.setModel(createTableModelFromList(clients));
        }

        List<Product> products = productBLL.findAll();
        if(products!=null){
            productTable.setModel(createTableModelFromList(products));
        }

        List<CustomerOrder> orders = orderBLL.findAll();
        if(orders!=null){
            orderTable.setModel(createTableModelFromList(orders));
        }

        List<Bill> bills = billBLL.findAll();
        if(bills!=null){
            billTable.setModel(createTableModelFromList(bills));
        }
    }

    private void clearClientFields() {
        clIdField.setText("");
        clNameField.setText("");
        clEmailField.setText("");
        clAddressField.setText("");
        clPhoneField.setText("");
    }

    private void clearProductFields() {
        prIdField.setText("");
        prNameField.setText("");
        prStockField.setText("");
        prPriceField.setText("");
        prCategoryField.setText("");
    }

    private void clearOrderFields() {
        ordIdField.setText("");
        ordClientIdField.setText("");
        ordProductIdField.setText("");
        ordQuantityField.setText("");
    }

    private <T> DefaultTableModel createTableModelFromList(List<T> objects) {
        if(objects == null || objects.isEmpty()){
            return new DefaultTableModel();
        }

        Class<?> clas=objects.get(0).getClass();
        Field[] fields = clas.getDeclaredFields();

        String[] columns= Arrays.stream(fields).map(Field::getName).toArray(String[]::new);


        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Field field : fields) {
            field.setAccessible(true);
        }

        for (Object obj : objects) {
            Object[] row = new Object[fields.length];

            for (int i = 0; i < fields.length; i++) {
                try {
                    row[i] = fields[i].get(obj);
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                    row[i] = null;
                }
            }

            model.addRow(row);
        }

        return model;
    }

}
