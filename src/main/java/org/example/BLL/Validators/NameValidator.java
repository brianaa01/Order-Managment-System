package org.example.BLL.Validators;

import org.example.Model.Client;

public class NameValidator implements Validator<Client>{

    @Override
    public void validate(Client t) {
        if(t.getName() == null || t.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Invalid name");
        }
    }

}
