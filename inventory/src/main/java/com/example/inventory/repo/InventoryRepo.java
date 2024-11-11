package com.example.inventory.repo;

import com.example.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    @Query(value = "Select * from inventory where id=?1", nativeQuery = true)
    Inventory getItemById(Integer itemId);
}
