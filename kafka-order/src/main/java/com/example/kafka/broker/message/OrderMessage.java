package com.example.kafka.broker.message;

import com.example.kafka.util.LocalDateTimeDeserializer;
import com.example.kafka.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class OrderMessage {

    private Long orderId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime orderDateTime;

    private String creditCardNumber;

    private String orderLocation;

    private String itemName;

    private double price;

    private int quantity;

    public OrderMessage() {
    }

    public OrderMessage(Long orderId, LocalDateTime orderDateTime, String creditCardNumber, String orderLocation, String itemName, double price, int quantity) {
        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.creditCardNumber = creditCardNumber;
        this.orderLocation = orderLocation;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "orderId=" + orderId +
                ", orderDateTime=" + orderDateTime +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", orderLocation='" + orderLocation + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
