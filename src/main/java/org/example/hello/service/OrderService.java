package org.example.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.example.hello.model.Client;
import org.example.hello.model.Order;
import org.example.hello.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;

    @Autowired
    OrderService(OrderRepository orderRepository) { this.repository = orderRepository; }

    public Order getOrderById(String orderId) {
        Optional<Order> order = repository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.");
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order createOrder(Order order) {

        // TODO: Check if client exists
        // TODO: Check if meals exist (also save as mealId)

        return repository.save(order);
    }

    public Order updateOrder(String orderId, Order request) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.");
        }

        Order order = optionalOrder.get();
        order.updateOrder(request);

        return repository.save(order);
    }

    public Order deleteOrderById(String orderId) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.");
        }

        Order order = optionalOrder.get();

        repository.deleteById(orderId);
        return order;
    }
}
