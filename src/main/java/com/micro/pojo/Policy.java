package com.micro.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.micro.cassandra.CassandraConnector;
import com.micro.codec.RuleTypeCodec;
import com.micro.policy.common.Constants;

public class Policy {
	private List<Rule> rules= new ArrayList<>();
	private String createdByTenant;
	private List<Map<String, String>> machines= new ArrayList<>();
	private List<String> groups= new ArrayList<>();
	private List<String> tags= new ArrayList<>();
	private List<String> tenants= new ArrayList<>();
	private boolean isPrivate;
	private String policyName;
	private boolean isActive;

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public String getCreatedByTenant() {
		return createdByTenant;
	}

	public void setCreatedByTenant(String createdByTenant) {
		this.createdByTenant = createdByTenant;
	}

	public List<Map<String, String>> getMachines() {
		return machines;
	}

	public void setMachines(List<Map<String, String>> machines) {
		this.machines = machines;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getTenants() {
		return tenants;
	}

	public void setTenants(List<String> tenants) {
		this.tenants = tenants;
	}

	public static Map<String, String> getColoumnsForPolicyTable() {
		Map<String, String> columns = new HashMap<>();
		columns.put("rules", "list<frozen<ruleType>>");
		columns.put("createdByTenant", "text");
		columns.put("machines", "list<frozen<map<text,text>>>");
		columns.put("groups", "list<text>");
		columns.put("tags", "list<text>");
		columns.put("tenants", "list<text>");
		columns.put("policyName", "text");
		columns.put("isPrivate", "boolean");
		columns.put("isActive", "boolean");
		columns.put("PRIMARY KEY", "(createdByTenant,policyName)");
		return columns;
	}

	public static Map<String, String> getColumnsForRuleType() {
		Map<String, String> columns = new HashMap<>();
		columns.put("ruleName", "text");
		columns.put("ruleExpression", "text");
		columns.put("ruleInterpreter", "text");
		columns.put("tenant", "text");
		columns.put("ruleType", "text");
		columns.put("isPrivate", "boolean");
		return columns;

	}

	public static void registerCodecForRule(CassandraConnector cassandraConnector, String keySpace) {
		CodecRegistry codecRegistry = cassandraConnector.getCluster().getConfiguration().getCodecRegistry();
		UserType ruleType = cassandraConnector.getCluster().getMetadata().getKeyspace(keySpace).getUserType("ruletype");
		TypeCodec<UDTValue> ruleTypeCodec = codecRegistry.codecFor(ruleType);
		RuleTypeCodec ruleCodec = new RuleTypeCodec(ruleTypeCodec, Rule.class);
		codecRegistry.register(ruleCodec);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdByTenant == null) ? 0 : createdByTenant.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + (isPrivate ? 1231 : 1237);
		result = prime * result + ((machines == null) ? 0 : machines.hashCode());
		result = prime * result + ((policyName == null) ? 0 : policyName.hashCode());
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((tenants == null) ? 0 : tenants.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Policy other = (Policy) obj;
		if (createdByTenant == null) {
			if (other.createdByTenant != null)
				return false;
		} else if (!createdByTenant.equals(other.createdByTenant))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (isActive != other.isActive)
			return false;
		if (isPrivate != other.isPrivate)
			return false;
		if (machines == null) {
			if (other.machines != null)
				return false;
		} else if (!machines.equals(other.machines))
			return false;
		if (policyName == null) {
			if (other.policyName != null)
				return false;
		} else if (!policyName.equals(other.policyName))
			return false;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (tenants == null) {
			if (other.tenants != null)
				return false;
		} else if (!tenants.equals(other.tenants))
			return false;
		return true;
	}
	
	
	
}
