package com.shipment.shipment_management.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Shipment {
    
    @Id
    @GeneratedValue
    private Integer shipmentId;
    
    private Integer orderId;
    
    private String destination;

    private String shipmentDate; 
}
 