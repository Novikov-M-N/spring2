package com.github.novikovmn.payload;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonAutoDetect
public class Payment {
    private String date;
    private BigDecimal amount;

    public Payment() {}

    public Payment(String date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "дата платежа: " + date + " сумма: " + amount;
    }
}
