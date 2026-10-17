package org.example.BLL.Validators;

import org.example.Model.CustomerOrder;

public class QuantityValidator implements Validator<CustomerOrder>{
    @Override
    public void validate(CustomerOrder o) {
        if(o.getQuantity()<=0){
            throw new IllegalArgumentException("Quantity must be greater than 0!");
        }
    }
}
