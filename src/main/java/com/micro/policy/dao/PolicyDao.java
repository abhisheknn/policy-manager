package com.micro.policy.dao;

import java.util.List;
import com.micro.pojo.Policy;

public interface PolicyDao {
	public Policy createPolicy(Policy policy) ;
	public List<Policy> getPolicy(String tenant,String policyName) ;
	public Policy updatePolicy();
}
