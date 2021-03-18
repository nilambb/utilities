package com.nets.utilities.microservcie.kafkadb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nets.utilities.microservcie.kafkadb.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
