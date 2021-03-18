package com.nets.utiltiy.bank.app.cardservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nets.utiltiy.bank.app.cardservice.entity.CardDetails;

public interface CardDetailsRepo extends JpaRepository<CardDetails, Integer> {

	Optional<CardDetails> getByCardNumber(String cardNumber);
	
}
