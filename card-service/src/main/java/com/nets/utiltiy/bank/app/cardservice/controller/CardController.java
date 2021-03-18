package com.nets.utiltiy.bank.app.cardservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nets.utiltiy.bank.app.cardservice.entity.CardDetails;
import com.nets.utiltiy.bank.app.cardservice.service.CardService;

@RestController
public class CardController {
	@Autowired
	CardService cardService;
	
	@GetMapping("/v1/cardManage")
	public List<CardDetails> list() {
	    return cardService.getAll();
	}
	
	@GetMapping("/v1/cardManage/{cardNumber}")
	public Optional<CardDetails> getCardByNumber(@PathVariable String cardNumber) {
	    return cardService.getByCardNumber(cardNumber);
	}
	
	@PostMapping("/v1/cardManage")
	public CardDetails save(@RequestBody CardDetails card) {
	    return cardService.save(card);
	}
	
	@PutMapping("/v1/cardManage/{cardNumber}")
	public String update(@PathVariable String cardNumber, @RequestBody CardDetails card) throws InterruptedException {
	    cardService.updateByCardNumber(cardNumber, card);
	    return "Processing the request";
	}
	
	@DeleteMapping("/v1/cardManage/{id}")
	public void delete(@PathVariable int id) {
		cardService.delete(id);
	}
}
