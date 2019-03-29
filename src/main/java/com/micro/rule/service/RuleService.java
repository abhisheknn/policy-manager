package com.micro.rule.service;

import java.util.List;

import com.micro.pojo.Rule;

public interface RuleService {

	public Rule createRule(Rule rule);
	public List<Rule> getRule(String tenant,String ruleName);
	//public List<Rule> getAllRules(String tenant);
	public Rule updateRule(Rule rule);
	
}
