package com.micro.rule.dao;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import com.micro.cassandra.Cassandra;
import com.micro.constant.AppConstants.ReplicationStrategy;
import com.micro.pojo.Rule;
import com.micro.policy.common.Constants;
import com.micro.policymanager.cassandra.CassandraConnector;

import org.junit.Assert;

public class RuleDaoImplTest {

	@org.junit.Rule
	 public final EnvironmentVariables environmentVariables
	    = new EnvironmentVariables();

	private CassandraConnector cassandraConnector=null;
	RuleDao ruleDao= new RuleDaoImpl();
	
	@Before
	public void setup() {
		try {
			environmentVariables.set("CASSANDRA_HOST", "127.0.0.1");	
			environmentVariables.set("CASSANDRA_PORT", "9142");
			EmbeddedCassandraServerHelper.startEmbeddedCassandra(50000L);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cassandraConnector = new CassandraConnector();
		ruleDao.setCassandraConnector(cassandraConnector);
	}

	@Test
	public void testCreateRuleForDockerXNamespace() {
		Rule rule= new Rule();
		rule.setIsPrivate(false);
		rule.setRuleName("expected");
		rule.setRuleExpression("expected");
		rule.setRuleInterpreter("expected");
		rule.setRuleType("expected");
		rule.setTenant("expected");
		ruleDao.createRule(rule);
		List<Rule> rulesFromDB =ruleDao.getRule(rule.getTenant(), rule.getRuleName());
		Assert.assertEquals(rule, rulesFromDB.get(0));
	}
	
	public  Map<String, String> getColoumnsForTenantTable() {
		Map<String, String> columnConf = new HashMap<>();
		columnConf.put("tenantId", "text");
		columnConf.put("JWToken", "text");
		columnConf.put("status", "text");
		columnConf.put("license", "text");
		//columnConf.put("controls", "map<text,text>");
		columnConf.put("cassandraKeySpace", "text");
		columnConf.put("PRIMARY KEY", "(tenantId)");
		return columnConf;
	}
	
	
	@Test
	public void testGetRuleForTenantSpecificKeySpace() {
		
		Cassandra.createKeySpace(cassandraConnector.getSession(), "dell", ReplicationStrategy.SimpleStrategy,1);
		Cassandra.createTable(cassandraConnector.getSession(), Constants.DOCKERKEYSPACE, Constants.TENANTTABLE,this.getColoumnsForTenantTable());
		String tenant= "{\"tenantId\":\"dell\",\"cassandraKeySpace\":\"dell\"}";
		Cassandra.insertJSON(cassandraConnector.getSession(), Constants.DOCKERKEYSPACE, Constants.TENANTTABLE, tenant);
		
		Rule rule1= new Rule();
		rule1.setIsPrivate(true);
		rule1.setRuleName("rule1");
		rule1.setRuleExpression("expected");
		rule1.setRuleInterpreter("expected");
		rule1.setRuleType("expected");
		rule1.setTenant("dell");
		ruleDao.createRule(rule1);
		
		Rule rule2= new Rule();
		rule2.setIsPrivate(true);
		rule2.setRuleName("rule2");
		rule2.setRuleExpression("expected");
		rule2.setRuleInterpreter("expected");
		rule2.setRuleType("expected");
		rule2.setTenant("dell");
		ruleDao.createRule(rule2);
		List<Rule> rulesFromDB =ruleDao.getRule(rule2.getTenant(), null);
		Assert.assertEquals(2, rulesFromDB.size());
		Assert.assertEquals(true, rulesFromDB.contains(rule2));
		Assert.assertEquals(true, rulesFromDB.contains(rule1));
	}
	
	
	@Test
	public void testCreateRuleForTenantSpecificKeySpace() {
		
		Cassandra.createKeySpace(cassandraConnector.getSession(), "dell", ReplicationStrategy.SimpleStrategy,1);
		Cassandra.createTable(cassandraConnector.getSession(), Constants.DOCKERKEYSPACE, Constants.TENANTTABLE,this.getColoumnsForTenantTable());
		String tenant= "{\"tenantId\":\"dell\",\"cassandraKeySpace\":\"dell\"}";
		Cassandra.insertJSON(cassandraConnector.getSession(), Constants.DOCKERKEYSPACE, Constants.TENANTTABLE, tenant);
		
		Rule rule= new Rule();
		rule.setIsPrivate(true);
		rule.setRuleName("expected");
		rule.setRuleExpression("expected");
		rule.setRuleInterpreter("expected");
		rule.setRuleType("expected");
		rule.setTenant("dell");
		ruleDao.createRule(rule);
		List<Rule> rulesFromDB =ruleDao.getRule(rule.getTenant(), rule.getRuleName());
		Assert.assertEquals(rule, rulesFromDB.get(0));
	}
	
}
