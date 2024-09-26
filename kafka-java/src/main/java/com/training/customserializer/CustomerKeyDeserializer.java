package com.training.customserializer;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.model.CustomerKey;

public class CustomerKeyDeserializer implements Deserializer<CustomerKey> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override
	public CustomerKey deserialize(String topic, byte[] bytes) {
		CustomerKey data = null;
		if (Objects.isNull(bytes)) {
			return null;
		}

		try {
			data = objectMapper.treeToValue(objectMapper.readTree(bytes), CustomerKey.class);
		} catch (JsonProcessingException e) {
			throw new SerializationException("Some problem occurred:" + e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

}
