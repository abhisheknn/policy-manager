package com.micro.policy.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.micro.client.RestClient;
import com.micro.pojo.Policy;
import com.micro.policy.common.Constants;
import com.micro.policy.dao.PolicyDao;

@Component
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyDao policyDao;

	@Autowired
	RestClient restClient;
	
	Gson gson= new GsonBuilder().disableHtmlEscaping().create();
	
	@Override
	public Policy createPolicy(Policy policy) {
		policyDao.createPolicy(policy);
		applyPolicy(policy);
		return policy;
	}

	private void applyPolicy(Policy policy) {
		
		try {
			restClient.doPost(Constants.C2SYSTEMURL+"/policy", gson.toJson(policy), null);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
