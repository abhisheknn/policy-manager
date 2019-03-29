package com.micro.pojo;

import java.util.List;
import java.util.Map;

public class Policy {
	private List<Rule> rules;
	private String createdByTenant;
	private List<Map<String,String>> machines;
	private List <String> groups;
	private List <String> tags;
	private List <String> tenants;
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
	
	
	
	
}
