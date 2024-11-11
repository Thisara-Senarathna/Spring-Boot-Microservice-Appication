package com.example.product.repo;

import com.example.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query(value = "select * from Product where id=?1",nativeQuery = true)
    Product getProductById(Integer productId);
}
