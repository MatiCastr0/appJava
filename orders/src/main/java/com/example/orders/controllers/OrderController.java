package com.example.orders.controllers;

import com.example.orders.model.Order;
import com.example.orders.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    /* Endpoint to retrieve all orders */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> listOrders() {
        return orderService.getOrders();
    }

    /* Endpoint to create a new order */
    @PostMapping
    public ResponseEntity<Object> addOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Error handling
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Object> response = orderService.newOrder(order);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            logger.info("Order created with ID: {}", order.getId());
        }
        return response;
    }

    /* Endpoint to update an existing order */
    @PutMapping("/{id}")
    public ResponseEntity<Object> modifyOrder(@PathVariable("id") Long id, @RequestBody Order updatedOrder) {
        ResponseEntity<Object> response = orderService.updateOrder(id, updatedOrder);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Order updated with ID: {}", id);
        }
        return response;
    }

    /* Endpoint to delete an order */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> removeOrder(@PathVariable("id") Long id) {
        ResponseEntity<Object> response = orderService.deleteOrder(id);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Order deleted with ID: {}", id);
        }
        return response;
    }
}
