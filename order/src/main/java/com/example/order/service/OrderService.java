package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.model.Orders;
import com.example.order.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.apache.catalina.UserDatabase;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders(){
        List<Orders> orders = orderRepo.findAll();
        return modelMapper.map(orders, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderDTO addOrder(OrderDTO orderDTO){
        Orders order = orderRepo.save(modelMapper.map(orderDTO,Orders.class));
        return orderDTO;
    }

    public OrderDTO updateOrder(OrderDTO orderDTO){
        Orders order =orderRepo.save(modelMapper.map(orderDTO,Orders.class));
        return orderDTO;
    }

    public String deleteOrder(Integer orderId){
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }

    public OrderDTO getOrderById(Integer orderId){
        Orders order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
}
