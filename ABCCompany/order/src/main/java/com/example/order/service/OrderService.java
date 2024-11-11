package com.example.order.service;

import com.example.inventory.dto.InventoryDTO;
import com.example.order.common.ErrorOrderResponse;
import com.example.order.common.OrderResponse;
import com.example.order.common.SuccessOrderResponse;
import com.example.order.dto.OrderDTO;
import com.example.order.model.Orders;
import com.example.order.repo.OrderRepo;
import com.example.product.dto.ProductDTO;
import jakarta.transaction.Transactional;
import org.apache.catalina.UserDatabase;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional

public class OrderService {
    private final WebClient inverntoryWebClient;
    private final WebClient productWebClient;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(@Qualifier("inventoryWebClient") WebClient inverntoryWebClient,@Qualifier("productWebClient") WebClient productWebClient, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.inverntoryWebClient = inverntoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders(){
        List<Orders> orders = orderRepo.findAll();
        return modelMapper.map(orders, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponse addOrder(OrderDTO orderDTO){

        Integer itemId = orderDTO.getItemId();

        try {
            InventoryDTO inventoryResponse =  inverntoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();


            System.out.println("Inventory response = "+inventoryResponse);
            assert inventoryResponse != null;

            Integer productId = inventoryResponse.getProductId();

            ProductDTO productResponse =  productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/product/{productId}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            assert productResponse != null;

            System.out.println("product response = "+ productResponse);

            if(inventoryResponse.getQuantity() > 0){
                if(productResponse.isForsale()){
                    System.out.println("order successful");
                    Orders order = orderRepo.save(modelMapper.map(orderDTO,Orders.class));
                }
                else{
                    System.out.println("product not for sale");
                    return new ErrorOrderResponse("The Item is not for sale");
                }
                return new SuccessOrderResponse(orderDTO);
            }
            else{
                System.out.println("quantity not available");
                return new ErrorOrderResponse("Item not available..");
            }
        }
        catch (WebClientResponseException e){
            if(e.getStatusCode().is5xxServerError()){
                return new ErrorOrderResponse("Item not found");
            }
        }
        return null;
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
