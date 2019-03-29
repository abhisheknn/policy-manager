package com.micro.pojo;

import java.util.HashMap;
import java.util.Map;

public class Rule {
	String ruleExpression ;
	String ruleInterpreter;
	String ruleName;
	String tenant;
    String ruleType;
    boolean isprivate;
    
    
	
	
	
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	public boolean isIsprivate() {
		return isprivate;
	}
	public void setIsprivate(boolean isprivate) {
		this.isprivate = isprivate;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleExpression() {
		return ruleExpression;
	}
	public void setRuleExpression(String ruleExpression) {
		this.ruleExpression = ruleExpression;
	}
	public String getRuleInterpreter() {
		return ruleInterpreter;
	}
	public void setRuleInterpreter(String ruleInterpreter) {
		this.ruleInterpreter = ruleInterpreter;
	}
	public static Map<String, String> getColoumnsForRuleTable() {
		Map<String, String> columns= new HashMap<>();
		columns.put("ruleName","text");
		columns.put("ruleExpression","text");
		columns.put("ruleInterpreter","text");
		columns.put("tenant","text");
		columns.put("ruleType","text");
		columns.put("isPrivate","boolean");
		columns.put("PRIMARY KEY","(tenant,ruleName)");
		return columns;
	}
}
