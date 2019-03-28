package com.micro.policy.cassandra;

import org.springframework.stereotype.Component;

import com.micro.cassandra.Cassandra;
import com.micro.constant.AppConstants.ReplicationStrategy;
import com.micro.policy.common.Constants;
import com.micro.rest.rule.Rule;

@Component
public class CassandraConnector extends com.micro.cassandra.CassandraConnector {
	public CassandraConnector() {
		String[] nodes = Constants.CASSANDRA_HOST.split(",");
		super.connect(nodes, Integer.parseInt(Constants.CASSANDRA_PORT));
		Cassandra.createKeySpace(this.getSession(), Constants.DOCKERKEYSPACE, ReplicationStrategy.SimpleStrategy, 1);
		Cassandra.createTable(this.getSession(), Constants.DOCKERKEYSPACE, Constants.RULETABLE,
				Rule.getColoumnsForRuleTable());
	}
}
