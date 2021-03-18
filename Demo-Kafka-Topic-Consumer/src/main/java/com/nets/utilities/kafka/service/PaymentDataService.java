package com.nets.utilities.kafka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nets.utilities.kafka.entity.PaymentData;
import com.nets.utilities.kafka.repository.PaymentDataRepo;

@Service
@Transactional
public class PaymentDataService {
	@Autowired
	PaymentDataRepo paymentDetailsRepo;

	public List<PaymentData> getAllpayment(){
		return paymentDetailsRepo.findAll();
	}
	
	public PaymentData save(PaymentData payment){
		return paymentDetailsRepo.save(payment);
	}
	
	public Optional<PaymentData> getById(int id) {
		return paymentDetailsRepo.findById(id);
	}
	
	public PaymentData updateById(int id, PaymentData data) {
		Optional<PaymentData> info = paymentDetailsRepo.findById(id);
		if(info.isPresent()) {
			PaymentData actualData = info.get();
			actualData.setAmount(data.getAmount());
			actualData.setCardNumber(data.getCardNumber());
			actualData.setDescription(data.getDescription());
			actualData.setStatus(data.getStatus());
			return paymentDetailsRepo.save(actualData);
		} else {
			return paymentDetailsRepo.save(data);
		}
				
	}

	public void delete(int id) {
		paymentDetailsRepo.deleteById(id);
	}
}
