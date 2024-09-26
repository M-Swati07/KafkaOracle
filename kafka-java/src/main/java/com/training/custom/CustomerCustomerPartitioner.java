package com.training.custom;

import java.util.Objects;

import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

import com.training.model.CustomerKey;

public class CustomerCustomerPartitioner extends DefaultPartitioner{

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		
		String partitionKey = null;
		if(Objects.nonNull(key))
		{
			CustomerKey customerKey = (CustomerKey) key;
			partitionKey = customerKey.getCustomerId();		//901
			keyBytes = partitionKey.getBytes();
		}
		
		return super.partition(topic, partitionKey, keyBytes, value, valueBytes, cluster);
	}
}
