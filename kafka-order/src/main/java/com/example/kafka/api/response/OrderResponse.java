package com.example.kafka.api.response;

public class OrderResponse {

    private Long orderNumber;

    public OrderResponse() {
    }

    public OrderResponse(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "orderNumber='" + orderNumber + '\'' +
                '}';
    }
}
