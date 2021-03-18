package com.nets.utilities.microservcie.kafkadb.listner;

import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
class CardDetails {
	private Integer id;
	private String cardNumber;
	private String cardStatus;
	private String reason;
}

@Component
public class InvalidPaymentListner {
	@Autowired
	RestTemplate restTemplate;

	@Value("${card_block_url}")
	private String url;

	public InvalidPaymentListner() {
		System.out.println("InvalidPaymentListner initialized.............................");
	}

	@KafkaListener(topics = "payment-manager")
	public void listen(String message) {
		System.out.println("===========================================================================");
		System.out.println("Recived Message in filtered listener: " + message);
		JSONObject jsonObject = new JSONObject(message);
		String cardNumber = jsonObject.getJSONObject("payload").getString("card_number");
		String description = jsonObject.getJSONObject("payload").getString("description");
		System.out.println("Blocking card - " + cardNumber + "Description - " + description);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		CardDetails card = new CardDetails();
		card.setCardNumber(cardNumber);
		card.setCardStatus("BLOCK");
		card.setReason(description);
		System.out.println("url = " + url);
		HttpEntity<CardDetails> entity = new HttpEntity<CardDetails>(card, headers);
		System.out.println(restTemplate.exchange(url + "/" + cardNumber, HttpMethod.PUT, entity, String.class).getBody());

		System.out.println("===========================================================================");
	}
}
