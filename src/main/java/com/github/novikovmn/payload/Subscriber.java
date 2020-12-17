package com.github.novikovmn.payload;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonAutoDetect
public class Subscriber {
    private String name;
    private Address address;
    private Long phoneNumber;
    private List<Payment> payments;

    public Subscriber() {}

    public Subscriber(String name, Address address, Long phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.payments = new ArrayList<>();
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Имя: ").append(name).append("\n")
                .append("Адрес: ").append(address.toString()).append("\n")
                .append("Телефон: ").append(phoneNumber).append("\n")
                .append("Платежи:").append("\n");
        for (Payment payment : payments) {
            sb.append("    ").append(payment.toString()).append("\n");
        }
        return sb.toString();
    }
}
