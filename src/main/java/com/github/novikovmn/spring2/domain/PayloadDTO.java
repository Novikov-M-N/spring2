package com.github.novikovmn.spring2.domain;



import java.util.Date;
import java.util.Random;

public class PayloadDTO {
    private Integer orderNumber;
    private String message;
    private Date timestamp;

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setRandomInt(Integer randomInt) {
        this.randomInt = randomInt;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Integer getRandomInt() {
        return randomInt;
    }

    private Integer randomInt;

    public PayloadDTO(Integer orderNumber, String message) {
        this.orderNumber = orderNumber;
        this.message = message;
        this.timestamp = new Date();
        Random random = new Random();
        this.randomInt = random.nextInt();
    }
}
