package com.micro.codec;

import java.nio.ByteBuffer;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.exceptions.InvalidTypeException;
import com.micro.pojo.Policy;
import com.micro.pojo.Rule;

public class RuleTypeCodec extends TypeCodec<Rule> {

	private final TypeCodec<UDTValue> innerCodec;
	private final UserType userType;

	public RuleTypeCodec(TypeCodec<UDTValue> innerCodec, Class<Rule> javaClass) {
		super(innerCodec.getCqlType(), javaClass);
		this.innerCodec = innerCodec;
		this.userType = (UserType) innerCodec.getCqlType();
	}

	@Override
	public ByteBuffer serialize(Rule value, ProtocolVersion protocolVersion) throws InvalidTypeException {
		return innerCodec.serialize(toUDTValue(value), protocolVersion);
	}

	private UDTValue toUDTValue(Rule rule) {
		UDTValue udtvalue = null;
		if (null != rule) {
			udtvalue = userType.newValue().setBool("isprivate", rule.getIsPrivate())
					.setString("ruleexpression", rule.getRuleExpression())
					.setString("ruleinterpreter", rule.getRuleInterpreter()).setString("rulename", rule.getRuleName())
					.setString("ruletype", rule.getRuleType()).setString("tenant", rule.getTenant());
		}
		return udtvalue;
	}

	@Override
	public Rule deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) throws InvalidTypeException {
		return toRule(innerCodec.deserialize(bytes, protocolVersion));
	}

	@Override
	public Rule parse(String value) throws InvalidTypeException {
		return value == null || value.isEmpty() || value.equals("NULL") ? null : toRule(innerCodec.parse(value));
	}

	@Override
	public String format(Rule value) throws InvalidTypeException {
		return value == null ? null : innerCodec.format(toUDTValue(value));
	}

	protected Rule toRule(UDTValue value) {
		Rule rule = null;
		if (null != value) {
			rule = new Rule();
			rule.setIsPrivate(value.getBool("isprivate"));
			rule.setRuleExpression(value.getString("ruleExpression"));
			rule.setRuleInterpreter(value.getString("ruleInterpreter"));
			rule.setRuleName(value.getString("ruleName"));
			rule.setRuleType(value.getString("ruleType"));
			rule.setTenant(value.getString("tenant"));
		}
		return rule;
	}

}
