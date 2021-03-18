package com.nets.utilities.microservcie.kafkadb.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
public class PaymentData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String cardNumber;
	private float amount;
	private String status;
	private String description;
	
	@CreationTimestamp
	//@ColumnDefault("CURRENT_TIMESTAMP")
	protected LocalDateTime created;

	@UpdateTimestamp
	//@ColumnDefault("CURRENT_TIMESTAMP")
	protected LocalDateTime modified;
}
