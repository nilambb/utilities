package com.nets.utilities.microservcie.kafkadb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nets.utilities.microservcie.kafkadb.entity.PaymentData;

@Repository
public interface PaymentDataRepo extends JpaRepository<PaymentData, Integer>{

}
