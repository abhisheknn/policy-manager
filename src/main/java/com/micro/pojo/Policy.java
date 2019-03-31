package com.micro.pojo;

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
	private List<Rule> rules;
	private String createdByTenant;
	private List<Map<String,String>> machines;
	private List <String> groups;
	private List <String> tags;
	private List <String> tenants;
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
	
	
	public static Map<String,String> getColoumnsForPolicyTable(){
		Map<String, String> columns= new HashMap<>();
		columns.put("rules", "list<frozen<ruleType>>");
		columns.put("createdByTenant", "text");
		columns.put("machines", "list<frozen<map<text,text>>>");
		columns.put("groups", "list<text>");
		columns.put("tags", "list<text>");
		columns.put("tenants", "list<text>");
		columns.put("policyName","text");	
		columns.put("isPrivate","boolean");
		columns.put("isActive","boolean");
		columns.put("PRIMARY KEY","(createdByTenant,policyName)");
		return columns;
	}
	public static Map<String,String> getColumnsForRuleType(){
		Map<String, String> columns= new HashMap<>();
		columns.put("ruleName","text");
		columns.put("ruleExpression","text");
		columns.put("ruleInterpreter","text");
		columns.put("tenant","text");
		columns.put("ruleType","text");
		columns.put("isPrivate","boolean");
		return columns;
				
	}
	public static void registerCodecForRule(CassandraConnector cassandraConnector, String keySpace) {
		CodecRegistry  codecRegistry=cassandraConnector.getCluster().getConfiguration().getCodecRegistry();
		UserType ruleType = cassandraConnector.getCluster().getMetadata().getKeyspace(keySpace).getUserType("ruletype");
		TypeCodec<UDTValue> ruleTypeCodec = codecRegistry.codecFor(ruleType);
		RuleTypeCodec ruleCodec = new RuleTypeCodec(ruleTypeCodec,Rule.class);
		codecRegistry.register(ruleCodec);
	}
}
