package com.micro.policy.dao;

import java.util.List;
import com.micro.pojo.Policy;
import com.micro.policymanager.cassandra.CassandraConnector;

public interface PolicyDao {
	public Policy createPolicy(Policy policy);
	public List<Policy> getPolicy(String tenant, String policyName);
	public Policy updatePolicy(Policy policy);
	public void setCassandraConnector(CassandraConnector cassandraConnector);
}
