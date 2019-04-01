package com.micro.policy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.micro.pojo.Policy;
import com.micro.policy.dao.PolicyDao;

@Component
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyDao policyDao;

	@Override
	public Policy createPolicy(Policy policy) {
		return policyDao.createPolicy(policy);
	}

	@Override
	public List<Policy> getPolicy(String tenant, String policyName) {
		return policyDao.getPolicy(tenant, policyName);
	}

	@Override
	public Policy updatePolicy(Policy policy) {
		return policyDao.updatePolicy(policy);

	}

}
