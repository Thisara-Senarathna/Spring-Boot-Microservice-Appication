package com.example.inventory.controller;

import com.example.inventory.dto.InventoryDTO;
import com.example.inventory.service.InventoryService;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")

public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getitems")
    public List<InventoryDTO> getItems(){
        return inventoryService.getItems();
    }

    @PostMapping("/additem")
    public InventoryDTO addItem(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.addItem(inventoryDTO);
    }

    @PutMapping("/updateitem")
    public InventoryDTO updateItem(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.updateItem(inventoryDTO);
    }

    @DeleteMapping("/deleteitem/{itemId}")
    public String deleteItem(@PathVariable Integer itemId){
        return inventoryService.deleteItem(itemId);
    }

    @GetMapping("/getitembyid/{itemId}")
    public InventoryDTO getItemById(@PathVariable Integer itemId){
        return inventoryService.getItemById(itemId);
    }
}
