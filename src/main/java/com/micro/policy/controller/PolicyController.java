package com.micro.policy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.micro.pojo.Policy;

public interface PolicyController {
	public ResponseEntity<Policy> createPolicy(Policy policy);

	public ResponseEntity<List<Policy>> getPolicy(String tenant, String policyName);

	public ResponseEntity<Policy> updatePolicy(Policy policy);
}
