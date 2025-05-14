package com.app.restaurant.controller;

import com.app.restaurant.model.Order;
import com.app.restaurant.model.OrderStatus;
import com.app.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dotpay")
public class DotPayController {

    OrderRepository orderRepository;

    public DotPayController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("/dotpay")
    public String okStatus(@RequestParam("status") String status, HttpSession session) {

        if (status.equals("OK,OK")) {
            if (session.getAttribute("order") != null) {
                Order order = (Order) session.getAttribute("order");
                order.setOrderStatus(OrderStatus.Paid);  // changed status to PAID for English
                orderRepository.saveAndFlush(order);
            }
            session.removeAttribute("order");

            return "dotpay/orderOK";
        } else if (status.equals("FAIL,FAIL"))
            return "dotpay/orderFAIL";
        else return "index";
    }
}
