package com.example.kafka.api.request;

public class OrderCancelRequest {

    private long OrderId;

    public OrderCancelRequest(long orderId) {
        OrderId = orderId;
    }

    public OrderCancelRequest() {
    }

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderCancelRequest{" +
                "OrderId=" + OrderId +
                '}';
    }
}
