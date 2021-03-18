package com.nets.utilities.microservcie.kafkadb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nets.utilities.microservcie.kafkadb.entity.PaymentData;
import com.nets.utilities.microservcie.kafkadb.repository.PaymentDataRepo;

@Service
@Transactional
public class PaymentDataService {
	@Autowired
	PaymentDataRepo paymentDetailsRepo;

	public List<PaymentData> getAllpayment() {
		return paymentDetailsRepo.findAll();
	}

	public PaymentData save(PaymentData payment) {
		if (StringUtils.isEmpty(payment.getStatus())) {
			payment.setStatus("IN-PROGRESS");
		}
		return paymentDetailsRepo.save(payment);
	}

	public Optional<PaymentData> getById(int id) {
		return paymentDetailsRepo.findById(id);
	}

	public PaymentData updateById(int id, PaymentData data) {
		Optional<PaymentData> info = paymentDetailsRepo.findById(id);
		if (info.isPresent()) {
			PaymentData actualData = info.get();
			if (data.getAmount() > 0) {
				actualData.setAmount(data.getAmount());
			}
			if (!StringUtils.isEmpty(data.getCardNumber())) {
				actualData.setCardNumber(data.getCardNumber());
			}
			if (!StringUtils.isEmpty(data.getDescription())) {
				actualData.setDescription(data.getDescription());
			}
			if (!StringUtils.isEmpty(data.getStatus())) {
				actualData.setStatus(data.getStatus());
			}
			return paymentDetailsRepo.save(actualData);
		} else {
			return paymentDetailsRepo.save(data);
		}

	}

	public void delete(int id) {
		paymentDetailsRepo.deleteById(id);
	}
}
