package com.micro.policy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micro.pojo.Policy;
import com.micro.policy.service.PolicyService;

@RestController
@RequestMapping("/policy")
public class PolicyControllerImpl implements PolicyController {

	@Autowired
	PolicyService policyService;

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
		return ResponseEntity.status(HttpStatus.CREATED).body(policyService.createPolicy(policy));

	}

	@Override
	@RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Policy>> getPolicy(@RequestParam(value = "tenantid", required = false) String tenantid,
			@RequestParam(value = "policyName", required = false) String policyName) {
		return ResponseEntity.status(HttpStatus.CREATED).body(policyService.getPolicy(tenantid, policyName));
	}

	@Override
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Policy> updatePolicy(@RequestBody Policy policy) {

		return null;
	}
}
