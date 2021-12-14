package com.example.kafka.repositories;

import com.example.kafka.entities.CancelOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelOrderRepository extends JpaRepository<CancelOrder, Long > {

    CancelOrder save (CancelOrder cancelOrder);
}
