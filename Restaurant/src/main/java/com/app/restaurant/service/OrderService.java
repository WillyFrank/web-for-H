package com.app.restaurant.service;

import com.app.restaurant.model.Meal;
import com.app.restaurant.model.Order;
import com.app.restaurant.model.OrderStatus;
import com.app.restaurant.repository.CustomerRepository;
import com.app.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;

    public List<Meal> getAllMealsForOrder(Order order) {
        List<Meal> resultList = new ArrayList<>();
        for (Meal meal : order.getMeals()) {
            resultList.add(meal);
        }
        return resultList;
    }

    // Test for Heroku
    public BigDecimal calculatePriceForOrder(Order order) {
        BigDecimal price = new BigDecimal(0);
        for (Meal meal : order.getMeals()) {
            price = price.add(meal.getPrice());
        }
        return price;
    }

    public List<Order> getAllOrdersWithCompletedStatus() {
        List<Order> filteredList = orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getPriority))
                .filter(order -> order.getOrderStatus().equals(OrderStatus.Completed))
                .collect(Collectors.toList());
        return filteredList;
    }

    public List<Order> findAllByOrderStatusNotCompleted() {
        List<Order> filteredList = orderRepository.findAll().stream()
                .filter(order -> !order.getOrderStatus().equals(OrderStatus.Completed))
                .sorted(Comparator.comparing(Order::getPriority).reversed())
                .collect(Collectors.toList());
        return filteredList;
    }
}
