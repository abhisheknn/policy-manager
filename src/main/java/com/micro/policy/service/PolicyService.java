package com.micro.policy.service;

import java.util.List;
import com.micro.pojo.Policy;
public interface PolicyService {
	public Policy createPolicy(Policy Policy) ;
	public List<Policy> getPolicy(String tenant,String policyName) ;
	public Policy updatePolicy();
}
