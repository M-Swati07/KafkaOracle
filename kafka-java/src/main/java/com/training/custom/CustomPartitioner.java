package com.training.custom;

import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

public class CustomPartitioner extends DefaultPartitioner{

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
	
		return super.partition(topic, key, keyBytes, value, valueBytes, cluster);
		//return 3;

	}
}
