package com.micro.policy.dao.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import com.micro.cassandra.Cassandra;
import com.micro.constant.AppConstants.ReplicationStrategy;
import com.micro.pojo.Policy;
import com.micro.pojo.Rule;
import com.micro.policy.common.Constants;
import com.micro.policy.dao.PolicyDao;
import com.micro.policy.dao.PolicyDaoImpl;
import com.micro.policymanager.cassandra.CassandraConnector;

public class PolicyDaoImplTest {
	@org.junit.Rule
	 public final EnvironmentVariables environmentVariables
	    = new EnvironmentVariables();

	private CassandraConnector cassandraConnector=null;
	PolicyDao policyDao= new PolicyDaoImpl();
	
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
		policyDao.setCassandraConnector(cassandraConnector);
	}
	
	

	@Test
	public void testCreatePolicyInDockerxconfKeySpace() {
		Policy expectedPolicy= new Policy();
		expectedPolicy.setCreatedByTenant("dockerx_all");
		expectedPolicy.setIsActive(true);
		expectedPolicy.setIsPrivate(false);
		expectedPolicy.setPolicyName("test");
		
		Rule rule1= new Rule();
		rule1.setIsPrivate(true);
		rule1.setRuleName("rule1");
		rule1.setRuleExpression("expected");
		rule1.setRuleInterpreter("expected");
		rule1.setRuleType("expected");
		rule1.setTenant("dell");
		Rule rule2= new Rule();
		rule2.setIsPrivate(true);
		rule2.setRuleName("rule2");
		rule2.setRuleExpression("expected");
		rule2.setRuleInterpreter("expected");
		rule2.setRuleType("expected");
		rule2.setTenant("dell");
		List<Rule> rules= new ArrayList<>();
		rules.add(rule1);
		rules.add(rule2);
		expectedPolicy.setRules(rules);
		policyDao.createPolicy(expectedPolicy);
		List<Policy> policies=policyDao.getPolicy(expectedPolicy.getCreatedByTenant(), expectedPolicy.getPolicyName());
		Assert.assertEquals(expectedPolicy, policies.get(0));
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
	public void testCreatePolicyInTenantKeySpace() {
		
		Cassandra.createKeySpace(cassandraConnector.getSession(), "dell", ReplicationStrategy.SimpleStrategy,1);
		Cassandra.createTable(cassandraConnector.getSession(), Constants.DOCKERKEYSPACE, Constants.TENANTTABLE,this.getColoumnsForTenantTable());
		String tenant= "{\"tenantId\":\"dell\",\"cassandraKeySpace\":\"dell\"}";
		Cassandra.insertJSON(cassandraConnector.getSession(), Constants.DOCKERKEYSPACE, Constants.TENANTTABLE, tenant);
		
		
		Policy expectedPolicy= new Policy();
		expectedPolicy.setCreatedByTenant("dell");
		expectedPolicy.setIsActive(true);
		expectedPolicy.setIsPrivate(true);
		expectedPolicy.setPolicyName("test");
		
		Rule rule1= new Rule();
		rule1.setIsPrivate(true);
		rule1.setRuleName("rule1");
		rule1.setRuleExpression("expected");
		rule1.setRuleInterpreter("expected");
		rule1.setRuleType("expected");
		rule1.setTenant("dell");
		Rule rule2= new Rule();
		rule2.setIsPrivate(true);
		rule2.setRuleName("rule2");
		rule2.setRuleExpression("expected");
		rule2.setRuleInterpreter("expected");
		rule2.setRuleType("expected");
		rule2.setTenant("dell");
		List<Rule> rules= new ArrayList<>();
		rules.add(rule1);
		rules.add(rule2);
		expectedPolicy.setRules(rules);
		policyDao.createPolicy(expectedPolicy);
		List<Policy> policies=policyDao.getPolicy(expectedPolicy.getCreatedByTenant(), expectedPolicy.getPolicyName());
		Assert.assertEquals(expectedPolicy, policies.get(0));
	}

}
