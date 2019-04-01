package com.micro.rule.dao;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.micro.cassandra.Cassandra;
import com.micro.policymanager.cassandra.CassandraConnector;
import com.micro.policy.common.Constants;
import com.micro.util.CassandraUtil;
import com.micro.pojo.Rule;

@Component
public class RuleDaoImpl implements RuleDao {

	Gson gson = new GsonBuilder().disableHtmlEscaping().create();

	@Autowired
	CassandraConnector cassandraConnector;

	
	
	public void setCassandraConnector(CassandraConnector cassandraConnector) {
		this.cassandraConnector = cassandraConnector;
	}

	@Override
	public Rule createRule(Rule rule) {
		String keySpace = null;
		insertOrUpdateRule(rule, true);
		return rule;
	}

	private void insertOrUpdateRule(Rule rule, boolean insert) {
		String keySpace;
		if (rule.getIsPrivate()) {
			keySpace = CassandraUtil.getKeySpaceForTenant(cassandraConnector.getSession(), rule.getTenant());
		} else {
			rule.setTenant(Constants.DOCKERXALL);
			keySpace = Constants.DOCKERKEYSPACE;
		}
		if (keySpace != null) {
			if (insert)
				createRuleTable(keySpace);
			Cassandra.insertJSON(cassandraConnector.getSession(), keySpace, Constants.RULETABLE, gson.toJson(rule));
		}
	}

	private void createRuleTable(String keySpace) {
		Cassandra.createTable(cassandraConnector.getSession(), keySpace, Constants.RULETABLE,
				Rule.getColoumnsForRuleTable());
	}

	@Override
	public List<Rule> getRule(String tenant, String ruleName) {
		ResultSet resultSet;
		List<Rule> rules = new ArrayList<>();
		String keySpace = null;
		if (tenant.equals(Constants.DOCKERXALL)) {
			keySpace = Constants.DOCKERKEYSPACE;
		} else {
			keySpace = CassandraUtil.getKeySpaceForTenant(cassandraConnector.getSession(), tenant);
		}
		resultSet = getRulesFromCassandra(tenant, ruleName, keySpace);
		getRules(resultSet, rules);
		return rules;
	}

	private ResultSet getRulesFromCassandra(String tenant, String ruleName, String keySapce) {
		ResultSet resultSet;
		if (null == ruleName) {
			resultSet = Cassandra.select(cassandraConnector.getSession(), keySapce, Constants.RULETABLE, "*",
					"tenant='" + tenant + "'");
		} else {
			resultSet = Cassandra.select(cassandraConnector.getSession(), keySapce, Constants.RULETABLE, "*",
					"tenant='" + tenant + "' and " + "ruleName='" + ruleName + "'");
		}
		return resultSet;
	}

	private void getRules(ResultSet resultSet, List<Rule> rules) {
		List<Row> rows = resultSet.all();
		for (Row row : rows) {
			Rule rule = new Rule();
			rule.setIsPrivate(row.getBool("isPrivate"));
			rule.setRuleExpression(row.getString("ruleExpression"));
			rule.setRuleInterpreter(row.getString("ruleInterpreter"));
			rule.setTenant(row.getString("tenant"));
			rule.setRuleType(row.getString("ruleType"));
			rule.setRuleName(row.getString("ruleName"));
			rules.add(rule);
		}
	}

	@Override
	public Rule updateRule(Rule rule) {
		insertOrUpdateRule(rule, false);
		return rule;
	}
}
