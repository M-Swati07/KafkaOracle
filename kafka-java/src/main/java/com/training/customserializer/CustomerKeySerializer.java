package com.training.customserializer;

import java.util.Map;
import java.util.Objects;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.model.CustomerKey;

public class CustomerKeySerializer implements Serializer<CustomerKey> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override
	public byte[] serialize(String topic, CustomerKey data) {

		if (Objects.isNull(data)) {
			return null;
		} else {
			try {
				objectMapper.writeValueAsBytes(data);
			} catch (JsonProcessingException e) {
				throw new SerializationException("Some problem occurred:"+e);
			}
		}
		return null;
	}

}
