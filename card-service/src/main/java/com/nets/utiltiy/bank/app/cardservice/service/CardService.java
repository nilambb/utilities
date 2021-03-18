package com.nets.utiltiy.bank.app.cardservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nets.utiltiy.bank.app.cardservice.entity.CardDetails;
import com.nets.utiltiy.bank.app.cardservice.repository.CardDetailsRepo;
@Service
@Transactional
public class CardService {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(CardService.class);
    
	@Autowired
	CardDetailsRepo cardDetailsRepo;

	public List<CardDetails> getAll() {
		return cardDetailsRepo.findAll();
	}

	public Optional<CardDetails> getByCardNumber(String cardNumber) {
		return cardDetailsRepo.getByCardNumber(cardNumber);
	}

	public Optional<CardDetails> getById(int id) {
		return cardDetailsRepo.findById(id);
	}
	
	public CardDetails save(CardDetails card) {
		return cardDetailsRepo.save(card);
	}
	
	@Async("asyncExecutor")
	public void updateByCardNumber(String cardNumber, CardDetails card) throws InterruptedException {
		log.info("updating the card status for card - {} and card - {}", cardNumber, card);
		Thread.sleep(30000L); // Intentional delay
		log.info("got the cards details completed");

		Optional<CardDetails> data = getByCardNumber(cardNumber);
		if (data.isPresent()) {
			log.info("Card is present blocking the card");
			CardDetails actualCard = data.get();
			if (!StringUtils.isEmpty(card.getCardStatus())) {
				actualCard.setCardStatus(card.getCardStatus());
				actualCard.setReason(card.getReason());
			}
			cardDetailsRepo.save(actualCard);
		} else {
			log.info("the card provided is not present can not process the request");
		}
		

	}

	public void delete(int id) {
		cardDetailsRepo.deleteById(id);
	}
}
