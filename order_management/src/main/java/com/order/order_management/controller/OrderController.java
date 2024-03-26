package com.order.order_management.controller;

import com.order.order_management.entity.Order;
import com.order.order_management.repository.OrderRepository;

import org.springframework.web.client.RestTemplate; 
import org.springframework.http.HttpHeaders; // Use Spring's HttpHeaders
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    
    private OrderRepository orderRepository;
    private RestTemplate restTemplate; // Added RestTemplate

    public OrderController(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate; // Initialized RestTemplate
    }

    // Get all orders
    @GetMapping("/orders")
    public List<Order> getOrders() {
        return this.orderRepository.findAll();
    } 

    // Get order by id
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrderFromId(@PathVariable int orderId) {
        Optional<Order> order = this.orderRepository.findById(orderId);
        if(order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Add order
    @PostMapping("/order")
    public ResponseEntity<Order> addOrdResponseEntity(@RequestBody Order order) {
        Date date = new Date(); 
        order.setOrderDate(date);
        return new ResponseEntity<>(this.orderRepository.save(order),HttpStatus.OK);
    }
    
    // Update order by id
    @PutMapping("order/{orderId}")
    public ResponseEntity<Order> addOrdResponseEntity(@PathVariable int  orderId, @RequestBody Order order) {
        if(order != null)
            return new ResponseEntity<>(this.orderRepository.save(order),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Delete order by id
    @DeleteMapping("order/{orderId}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable int orderId) {
        this.orderRepository.deleteById(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/order/{orderId}/placeShipment/{destination}")
    public ResponseEntity<String> createShipment(@PathVariable int orderId,@PathVariable String destination) {
        Optional<Order> orderOptional =  this.orderRepository.findById(orderId); 
        if(!orderOptional.isPresent()){
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
        // call Shipment microservice post method to create this shipment 
        Date date = new Date(); 

        String shipmentJson = "{\"orderId\":" + orderId + ", \"destination\":\"" + destination + "\", \"shipmentDate\":\"" + date.toString() + "\"}";

        // Set up HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // Make HTTP POST request to Shipment microservice
        String shipmentServiceUrl = "http://localhost:8080/shipments"; // Assuming endpoint URL
        HttpEntity<String> requestEntity = new HttpEntity<>(shipmentJson, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(shipmentServiceUrl, HttpMethod.POST, requestEntity, String.class);

        // Check response status and return appropriate response
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>("Shipment created successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to create shipment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
