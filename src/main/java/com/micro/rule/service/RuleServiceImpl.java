package com.micro.rule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.micro.pojo.Rule;
import com.micro.rule.dao.RuleDao;

@Component
public class RuleServiceImpl implements RuleService {

	@Autowired
	RuleDao ruleDao;

	@Override
	public Rule createRule(Rule rule) {
		return ruleDao.createRule(rule);
	}

	@Override
	public List<Rule> getRule(String tenant, String ruleName) {
		return ruleDao.getRule(tenant, ruleName);
	}

	// @Override
	// public List<Rule> getAllRules(String tenant) {
	// return ruleDao.getAllRules(tenant);
	// }

	@Override
	public Rule updateRule(Rule rule) {
		return ruleDao.updateRule(rule);
	}
}
