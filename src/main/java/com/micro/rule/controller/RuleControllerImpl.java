package com.micro.rule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micro.pojo.Rule;
import com.micro.rule.service.RuleService;

@RestController
@RequestMapping("/rule")
public class RuleControllerImpl implements RuleController {

	@Autowired
	RuleService ruleservice;

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "*")
	public ResponseEntity<Rule> createRule(@RequestBody Rule rule) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ruleservice.createRule(rule));
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Rule>> getRule(@RequestParam(required = false, value = "tenant") String tenant,
			@RequestParam(required = false, value = "ruleName") String ruleName) {
		return ResponseEntity.ok(ruleservice.getRule(tenant, ruleName));
	}

	@Override
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rule> updateRule(@RequestBody Rule rule) {
		return ResponseEntity.ok(ruleservice.updateRule(rule));
	}
}
