package com.micro.pojo;

import java.util.HashMap;
import java.util.Map;

public class Rule {
	String ruleExpression;
	String ruleInterpreter;
	String ruleName;
	String tenant;
	String ruleType;
	boolean isPrivate;

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

	public boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(boolean isprivate) {
		this.isPrivate = isprivate;
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
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isPrivate ? 1231 : 1237);
		result = prime * result + ((ruleExpression == null) ? 0 : ruleExpression.hashCode());
		result = prime * result + ((ruleInterpreter == null) ? 0 : ruleInterpreter.hashCode());
		result = prime * result + ((ruleName == null) ? 0 : ruleName.hashCode());
		result = prime * result + ((ruleType == null) ? 0 : ruleType.hashCode());
		result = prime * result + ((tenant == null) ? 0 : tenant.hashCode());
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
		Rule other = (Rule) obj;
		if (isPrivate != other.isPrivate)
			return false;
		if (ruleExpression == null) {
			if (other.ruleExpression != null)
				return false;
		} else if (!ruleExpression.equals(other.ruleExpression))
			return false;
		if (ruleInterpreter == null) {
			if (other.ruleInterpreter != null)
				return false;
		} else if (!ruleInterpreter.equals(other.ruleInterpreter))
			return false;
		if (ruleName == null) {
			if (other.ruleName != null)
				return false;
		} else if (!ruleName.equals(other.ruleName))
			return false;
		if (ruleType == null) {
			if (other.ruleType != null)
				return false;
		} else if (!ruleType.equals(other.ruleType))
			return false;
		if (tenant == null) {
			if (other.tenant != null)
				return false;
		} else if (!tenant.equals(other.tenant))
			return false;
		return true;
	}

	public static Map<String, String> getColoumnsForRuleTable() {
		Map<String, String> columns = new HashMap<>();
		columns.put("ruleName", "text");
		columns.put("ruleExpression", "text");
		columns.put("ruleInterpreter", "text");
		columns.put("tenant", "text");
		columns.put("ruleType", "text");
		columns.put("isPrivate", "boolean");
		columns.put("PRIMARY KEY", "(tenant,ruleName)");
		return columns;
	}
}
