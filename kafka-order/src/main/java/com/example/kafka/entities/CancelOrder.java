package com.example.kafka.entities;

import com.example.kafka.util.LocalDateTimeDeserializer;
import com.example.kafka.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CancelOrder {

    @Id
    private Long order_id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime canceledOn;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Order order;

    public CancelOrder(Long order_id, LocalDateTime canceledOn, Order order) {
        this.order_id = order_id;
        this.canceledOn = canceledOn;
        this.order = order;
    }

    public CancelOrder() {
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public LocalDateTime getCanceledOn() {
        return canceledOn;
    }

    public void setCanceledOn(LocalDateTime canceledOn) {
        this.canceledOn = canceledOn;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "CancelOrder{" +
                "order_id=" + order_id +
                ", canceledOn=" + canceledOn +
                ", order=" + order +
                '}';
    }
}
