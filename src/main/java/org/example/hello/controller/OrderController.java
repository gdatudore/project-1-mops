package org.example.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.example.hello.model.Client;
import org.example.hello.model.Order;
import org.example.hello.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping
    public Order registerOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PatchMapping("/{orderId}")
    public Order updateOrder(@PathVariable String orderId, @RequestBody Order order) {
        return orderService.updateOrder(orderId, order);
    }

    @DeleteMapping("/{orderId}")
    public Order deleteOrder(@PathVariable String orderId) {
        return orderService.deleteOrderById(orderId);
    }
}
