package com.nets.utilities.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nets.utilities.kafka.entity.PaymentData;

@Repository
public interface PaymentDataRepo extends JpaRepository<PaymentData, Integer>{

}
