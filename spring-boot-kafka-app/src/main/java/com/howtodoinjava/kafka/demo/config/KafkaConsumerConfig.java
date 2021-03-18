package com.howtodoinjava.kafka.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	@Value("${kafka.ssl.keystore}")
	private String keyStorePath;
	@Value("${kafka.ssl.truststore}")
	private String trustStorePath;

	@Autowired
	private ConsumerFactory<Integer, String> consumerFactory;

	public Map<String, Object> consumerConfig() {
		Map<String, Object> kafkaAutoConfig = ((DefaultKafkaConsumerFactory<Integer, String>) consumerFactory)
				.getConfigurationProperties();
		Map<String, Object> consumerConfig = new HashMap<>();

		consumerConfig.putAll(kafkaAutoConfig);
		consumerConfig.put("ssl.truststore.location", trustStorePath);
		consumerConfig.put("ssl.keystore.location",  keyStorePath) ;
		//consumerConfig.put("ssl.client.auth", "required");
		//consumerConfig.put("ssl.keymanager.algorithm", "SunX509");
		consumerConfig.put("ssl.trustmanager.algorithm", "SunX509");
		//consumerConfig.put("ssl.keystore.type", "JKS");
		//consumerConfig.put("ssl.truststore.type", "JKS");
		//consumerConfig.put("security.inter.broker.protocol", "SSL");
		//consumerConfig.put("ssl.protocol", "SSL");
		//consumerConfig.put("ssl.endpoint.identification.algorithm", "");
		consumerConfig.compute(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, (k, v) -> (String) v);
		consumerConfig.compute(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, (k, v) -> (String) v);
		consumerConfig.compute(SslConfigs.SSL_KEY_PASSWORD_CONFIG, (k, v) -> (String) v);
		return consumerConfig;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfig()));
		return factory;
	}
}
