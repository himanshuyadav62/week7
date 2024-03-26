package com.shipment.shipment_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipment.shipment_management.entity.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer>{
    
}
