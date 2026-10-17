package org.example.BLL.Validators;

import org.example.Model.Product;

public class PriceValidator implements Validator<Product>{
    @Override
    public void validate(Product p) {
        if(p.getPrice()<=0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }
}
