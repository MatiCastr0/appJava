package com.example.orders.services;

import com.example.orders.model.Order;
import com.example.orders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final OrderRepository orderRepository;


    /* Retrieve all orders */
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    /* Create a new order */
    public ResponseEntity<Object> newOrder(Order order) {
        Long productId = order.getProductId();
        String url = "http://localhost:8083/api/products/find/" + productId;
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Product found
                Order savedOrder = orderRepository.save(order);
                return new ResponseEntity<>("Order created with ID " + savedOrder.getId(), HttpStatus.CREATED);
            } else {
                // Product not found
                return new ResponseEntity<>("Product not found, order not created", HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return new ResponseEntity<>("Product not found, order not created", HttpStatus.NOT_FOUND);
        }
    }

    /* Delete an order */
    public ResponseEntity<Object> deleteOrder(Long id) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            orderRepository.deleteById(id);
            return new ResponseEntity<>("Order deleted with ID " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }

    /* Update an existing order */
    public ResponseEntity<Object> updateOrder(Long id, Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setProductId(updatedOrder.getProductId());
            existingOrder.setUnitPrice(updatedOrder.getUnitPrice());
            existingOrder.setQuantity(updatedOrder.getQuantity());
            existingOrder.setTotal(updatedOrder.getTotal());
            existingOrder.setDate(updatedOrder.getDate());
            existingOrder.setNotes(updatedOrder.getNotes());

            orderRepository.save(existingOrder);
            return new ResponseEntity<>("Order updated with ID " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }
}
