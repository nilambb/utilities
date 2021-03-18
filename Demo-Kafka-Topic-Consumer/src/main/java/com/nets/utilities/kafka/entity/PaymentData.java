package com.nets.utilities.kafka.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity(name = "nets_payments_data")
public class PaymentData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer cardNumber;
	private float amount;
	private String status;
	private String description;
	@CreatedDate
	@Column(name = "created_date")
	private Date createdDate;
	@LastModifiedDate
	@Column(name = "last_modified_date")
	private Date lastModifiedDate;
}
