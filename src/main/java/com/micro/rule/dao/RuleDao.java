package com.micro.rule.dao;

import java.util.List;

import com.micro.pojo.Rule;
import com.micro.policymanager.cassandra.CassandraConnector;

public interface RuleDao {
	public Rule createRule(Rule rule);
	public List<Rule> getRule(String tenant, String ruleName);
	public Rule updateRule(Rule rule);
	public void setCassandraConnector(CassandraConnector connector) ;
}
