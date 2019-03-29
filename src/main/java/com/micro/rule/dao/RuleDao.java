package com.micro.rule.dao;

import java.util.List;

import com.micro.pojo.Rule;

public interface RuleDao {
	public Rule createRule(Rule rule);
	public List<Rule> getRule(String tenant,String ruleName);
	//public List<Rule> getAllRules(String tenant);
	public Rule updateRule(Rule rule);
}
