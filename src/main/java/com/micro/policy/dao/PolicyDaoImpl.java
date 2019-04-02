package com.micro.policy.dao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.CodecNotFoundException;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.micro.cassandra.Cassandra;
import com.micro.client.RestClient;
import com.micro.policymanager.cassandra.CassandraConnector;
import com.micro.pojo.Policy;
import com.micro.pojo.Rule;
import com.micro.policy.common.Constants;
import com.micro.util.CassandraUtil;

@Component
public class PolicyDaoImpl implements PolicyDao {

	@Autowired
	CassandraConnector cassandraConnector;
	
	
	
	public void setCassandraConnector(CassandraConnector cassandraConnector) {
		this.cassandraConnector = cassandraConnector;
	}

	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	Type listOfMapType = new TypeToken<List<Map<String, String>>>() {
	}.getType();
	Type listType = new TypeToken<List<String>>() {
	}.getType();

	@Override
	public Policy createPolicy(Policy policy) {
		return insertOrUpdatePolicy(policy, true);
	}

	private Policy insertOrUpdatePolicy(Policy policy, boolean insert) {
		String keySpace;
		if (policy.getIsPrivate()) {
			keySpace = CassandraUtil.getKeySpaceForTenant(cassandraConnector.getSession(), policy.getCreatedByTenant());
		} else {
			keySpace = Constants.DOCKERKEYSPACE;
		}
		if (insert) {
			createPolicyTable(policy, keySpace); // optimize this
		}
		Cassandra.insertJSON(cassandraConnector.getSession(), keySpace, Constants.POLICYTABLE, gson.toJson(policy));
		return policy;
	}

	

	private void createPolicyTable(Policy policy, String keySpace) {
		Cassandra.createType(cassandraConnector.getSession(), keySpace, Constants.RULETYPE,
				Policy.getColumnsForRuleType());
		Cassandra.createTable(cassandraConnector.getSession(), keySpace, Constants.POLICYTABLE,
				Policy.getColoumnsForPolicyTable());
		Policy.registerCodecForRule(cassandraConnector, keySpace);
	}

	@Override
	public List<Policy> getPolicy(String tenant, String policyName) {
		ResultSet resultSet;
		List<Policy> policies = new ArrayList<>();
		String keySpace = null;
		if (tenant.equals(Constants.DOCKERXALL)) {
			keySpace = Constants.DOCKERKEYSPACE;
		} else {
			keySpace = CassandraUtil.getKeySpaceForTenant(cassandraConnector.getSession(), tenant);
		}
		resultSet = getPoliciesFromCassandra(tenant, policyName, keySpace);
		getPolicies(resultSet, policies, keySpace);
		return policies;
	}

	private void getPolicies(ResultSet resultSet, List<Policy> policies, String keySpace) {
		List<Row> rows = resultSet.all();
		for (Row row : rows) {
			Policy policy = new Policy();
			policy.setIsPrivate(row.getBool("isPrivate"));
			policy.setCreatedByTenant(row.getString("createdByTenant"));
			List<Rule> rules = new ArrayList<>();
			try {
				rules = row.getList("rules", new TypeToken<Rule>() {
				});
			} catch (CodecNotFoundException ex) {
				Policy.registerCodecForRule(cassandraConnector, keySpace);
				rules = row.getList("rules", new TypeToken<Rule>() {
				});
			}
			policy.setRules(rules);
			policy.setPolicyName(row.getString("policyName"));
			policy.setIsActive(row.getBool("isActive"));
			List<Map<String, String>> machines = row.getList("machines", new TypeToken<Map<String, String>>() {
			});
			policy.setMachines(machines);
			List<String> groups = row.getList("groups", new TypeToken<String>() {
			});
			policy.setGroups(groups);
			List<String> tags = row.getList("tags", new TypeToken<String>() {
			});
			policy.setTags(groups);
			List<String> tenants = row.getList("tenants", new TypeToken<String>() {
			});
			policy.setTenants(tenants);
			policies.add(policy);
		}
	}

	private ResultSet getPoliciesFromCassandra(String tenant, String policyName, String keySapce) {
		ResultSet resultSet;
		if (null == policyName) {
			resultSet = Cassandra.select(cassandraConnector.getSession(), keySapce, Constants.POLICYTABLE, "*",
					"createdbytenant='" + tenant + "'");
		} else {
			resultSet = Cassandra.select(cassandraConnector.getSession(), keySapce, Constants.POLICYTABLE, "*",
					"createdbytenant='" + tenant + "' and " + "policyName='" + policyName + "'");
		}
		return resultSet;
	}

	@Override
	public Policy updatePolicy(Policy policy) {
		return insertOrUpdatePolicy(policy, false);

	}
}
