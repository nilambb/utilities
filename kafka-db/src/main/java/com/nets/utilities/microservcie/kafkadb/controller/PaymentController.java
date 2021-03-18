package com.nets.utilities.microservcie.kafkadb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nets.utilities.microservcie.kafkadb.entity.PaymentData;
import com.nets.utilities.microservcie.kafkadb.service.PaymentDataService;

@RestController
public class PaymentController {
	@Autowired
	PaymentDataService paymentDataService;
	
	@GetMapping("/v1/paymentData")
	public List<PaymentData> list() {
	    return paymentDataService.getAllpayment();
	}
	
	@PostMapping("/v1/paymentData")
	public PaymentData save(@RequestBody PaymentData data) {
	    return paymentDataService.save(data);
	}
	
	@PutMapping("/v1/paymentData/{id}")
	public PaymentData update(@PathVariable int id, @RequestBody PaymentData data) {
	    return paymentDataService.updateById(id, data);
	}
	
	@DeleteMapping("/v1/paymentData/{id}")
	public void delete(@PathVariable int id) {
		paymentDataService.delete(id);
	}
}
