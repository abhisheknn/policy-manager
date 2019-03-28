package com.micro.rest.rule.dao;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.ResolutionSyntax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.micro.cassandra.Cassandra;
import com.micro.policy.cassandra.CassandraConnector;
import com.micro.policy.common.Constants;
import com.micro.rest.rule.Rule;

@Component
public class RuleDaoImpl implements RuleDao {

	@Autowired
	CassandraConnector cassandraConnector;
	
	private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	
	@Override
	public Rule createRule(Rule rule) {
		if(rule.isIsprivate()) {
			//store the keyspace for tenant
			//get the keyspace for tenant 
			//
		}else {
			rule.setTenant(Constants.DOCKERXALL);
		Cassandra.insertJSON(cassandraConnector.getSession(),Constants.DOCKERKEYSPACE ,Constants.RULETABLE, gson.toJson(rule));
		}
		return rule;
	}

	@Override
	public List<Rule> getRule(String tenant,String ruleName) {
		ResultSet resultSet;
		List<Rule> rules=new ArrayList<>();
		String keySapce=null;
		if(tenant.equals(Constants.DOCKERXALL)) {
			keySapce=Constants.DOCKERKEYSPACE;
		} else {
			//store the keyspace for tenant
			//get the keyspace for tenant 
		}
		resultSet = getRulesFromCassandra(tenant, ruleName, keySapce);
		getRules(resultSet, rules);
		return rules;
	}

	private ResultSet getRulesFromCassandra(String tenant, String ruleName, String keySapce) {
		ResultSet resultSet;
		if(null==ruleName) {
			resultSet=Cassandra.select(cassandraConnector.getSession(),keySapce ,Constants.RULETABLE, "*", "tenant='"+tenant +"'");
		}else {
			resultSet=Cassandra.select(cassandraConnector.getSession(),keySapce ,Constants.RULETABLE, "*", "tenant='"+tenant +"' and " +"ruleName='"+ruleName+"'");
		}
		return resultSet;
	}

	private void getRules(ResultSet resultSet, List<Rule> rules) {
		List<Row> rows= resultSet.all();
		for(Row row:rows) {
			Rule rule=  new Rule();
			rule.setIsprivate(row.getBool("isPrivate"));
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
		String keySpace;
		List<Rule> rules= new ArrayList<>(1);
		if(rule.isIsprivate() && !rule.getTenant().equals(Constants.DOCKERXALL))	{
		keySpace="";
		}else {
			keySpace=Constants.DOCKERKEYSPACE;
		}
		Cassandra.insertJSON(cassandraConnector.getSession(),Constants.DOCKERKEYSPACE,Constants.RULETABLE,gson.toJson(rule));
		String tenant=rule.getTenant();
		String ruleName=rule.getRuleName();
		ResultSet resultSet=getRulesFromCassandra(tenant, ruleName, keySpace);
		getRules(resultSet, rules);
		return rules.get(0);
	}

}
