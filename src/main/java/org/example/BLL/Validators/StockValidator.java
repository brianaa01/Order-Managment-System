package org.example.BLL.Validators;

import org.example.Model.Product;

public class StockValidator implements Validator<Product>{
    @Override
    public void validate(Product p) {
        if(p.getStock()<0){
            throw new IllegalArgumentException("Stock must be a positive number!");
        }
    }

}
