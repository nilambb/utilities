package com.nets.utilities.kafka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nets.utilities.kafka.entity.PaymentData;
import com.nets.utilities.kafka.service.PaymentDataService;

@RestController("v1/paymentData/")
public class PaymentController {
	@Autowired
	PaymentDataService paymentDataService;
	
	@GetMapping
	public List<PaymentData> list() {
	    return paymentDataService.getAllpayment();
	}
	
	@PostMapping
	public PaymentData save(@RequestParam(required = true) PaymentData data) {
	    return paymentDataService.save(data);
	}
	
	@PutMapping("/{id}")
	public PaymentData update(@PathVariable int id, @RequestParam(required = true) PaymentData data) {
	    return paymentDataService.save(data);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		paymentDataService.delete(id);
	}
}
