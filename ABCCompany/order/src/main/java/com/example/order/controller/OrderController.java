package com.example.order.controller;

import com.example.base.dto.OrderEventDTO;
import com.example.order.common.OrderResponse;
import com.example.order.dto.OrderDTO;
import com.example.order.kafka.OrderProducer;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")

public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProducer orderProducer;

    @GetMapping("/getorders")
    public List<OrderDTO> getOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping("/addorder")
    public OrderResponse addOrder(@RequestBody OrderDTO orderDTO){
        OrderEventDTO orderEventDTO = new OrderEventDTO();
        orderEventDTO.setMessage("Order is commited");
        orderEventDTO.setStatus("pending");

        return orderService.addOrder(orderDTO);
    }

    @PutMapping("updateorder")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO){
        return orderService.updateOrder(orderDTO);
    }

    @DeleteMapping("/deleteorder/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId){
        return orderService.deleteOrder(orderId);
    }

    @GetMapping("/getorderbyid/{orderId}")
    public OrderDTO getOrderById(@PathVariable Integer orderId){
        return orderService.getOrderById(orderId);
    }

}
