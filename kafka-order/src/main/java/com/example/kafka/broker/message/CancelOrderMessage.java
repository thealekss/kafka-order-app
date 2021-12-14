package com.example.kafka.broker.message;


public class CancelOrderMessage {
    private Long orderId;

    public CancelOrderMessage(Long orderId) {
        this.orderId = orderId;
    }

    public CancelOrderMessage() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CancelOrderMessage{" +
                "orderId=" + orderId +
                '}';
    }
}
