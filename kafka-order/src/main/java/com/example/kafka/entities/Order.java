package com.example.kafka.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue()
    private Long orderId;

    /*@Column(nullable = false)
    private String orderNumber;*/

    @Column(nullable = false)
    private String creditCardNumber;

    @Column(nullable = false)
    private String orderLocation;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(mappedBy = "order",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<OrderItem> items;

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
        orderItem.setOrder(this);


    }

    public void removeItem(OrderItem orderItem) {
        items.remove(orderItem);
        orderItem.setOrder(null);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /*public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }*/


    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", orderLocation='" + orderLocation + '\'' +
                ", orderDateTime=" + orderDateTime +
                ", items=" + items +
                '}';
    }
}
