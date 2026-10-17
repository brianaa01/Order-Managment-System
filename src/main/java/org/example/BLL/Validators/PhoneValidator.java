package org.example.BLL.Validators;

import org.example.Model.Client;

import java.util.regex.Pattern;

public class PhoneValidator implements Validator<Client>{
    private static final String PHONE_PATTERN = "^[0-9]{10}$";

    @Override
    public void validate(Client t) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if(!pattern.matcher(t.getPhone()).matches()){
            throw new IllegalArgumentException("Invalid phone number");
        }

    }
}
