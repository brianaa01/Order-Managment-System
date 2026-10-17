package org.example.BLL;

import org.example.DAO.BillDAO;
import org.example.Model.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * Business Logic Layer (BLL) class for managing {@link Bill} transactions.
 * Intermediary layer between the presentation layer and DAO
 *
 * @author Bandila Briana
 * @version 1.0
 */

public class BillBLL {
    private BillDAO billDAO;

    /**
     * Instantiates a new BillBLL service and initializes its corresponding DAO component.
     */
    public BillBLL() {
        billDAO = new BillDAO();
    }

    /**
     * Validates and forwards a new bill record to the DAO to insert
     *
     * @param bill The immutable Bill record
     */
    public void insert(Bill bill){
        billDAO.insert(bill);
    }

    /**
     * Retrieves all bills from the database
     *
     * @return A list containing all recorded bills.
     */
    public List<Bill> findAll(){
        return billDAO.findAll();
    }
}
