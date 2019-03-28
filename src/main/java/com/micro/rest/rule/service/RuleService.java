package com.micro.rest.rule.service;

import java.util.List;

import com.micro.rest.rule.Rule;

public interface RuleService {

	public Rule createRule(Rule rule);
	public List<Rule> getRule(String tenant,String ruleName);
	//public List<Rule> getAllRules(String tenant);
	public Rule updateRule(String ruleName);
	
}
