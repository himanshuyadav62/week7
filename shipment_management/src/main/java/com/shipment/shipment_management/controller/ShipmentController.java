package com.shipment.shipment_management.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shipment.shipment_management.entity.Shipment;
import com.shipment.shipment_management.repository.ShipmentRepository;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;



@RestController
public class ShipmentController {
    private ShipmentRepository shipmentRepository; 

    public ShipmentController(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @PostMapping("/shipments")
    public ResponseEntity<?> postMethodName(@RequestBody Shipment shipment) {
        if(shipment != null)
            return ResponseEntity.ok(shipmentRepository.save(shipment));
        
        return ResponseEntity.badRequest().body("Shipment is null");
    } 

    @GetMapping("/order/{orderId}/shipments")
    public ResponseEntity<List<Shipment>> getShipmentDetails(@PathVariable int orderId) {
        List<Shipment> shipments = shipmentRepository.findAll();
        List<Shipment> shipmentWithOrder = new ArrayList<>();
        for(Shipment shipment : shipments) {
            if(shipment.getOrderId() == orderId){
                shipmentWithOrder.add(shipment);
			}     
        }
        return ResponseEntity.ok(shipmentWithOrder);
    }    
}
