package com.github.novikovmn.payload;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class Address {
    private String street;
    private String houseNumber;
    private Integer flat;

    public Address() {}

    public Address(String street, String houseNumber, Integer flat){
        this.street = street;
        this.houseNumber = houseNumber;
        this.flat = flat;
    }

    @Override
    public String toString() {
        return "ул. " + street + ", " + houseNumber + ", кв. " + flat;
    }
}
