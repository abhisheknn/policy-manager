package com.micro.util;

import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.micro.cassandra.Cassandra;
import com.micro.policy.common.Constants;
import com.datastax.driver.core.Session;

public class CassandraUtil {

	public static String getKeySpaceForTenant(Session session,String tenant) {
		String keySpace = null;
		ResultSet resultSet = null;
		if (null != tenant) {
			resultSet = Cassandra.select(session, Constants.DOCKERKEYSPACE,
					Constants.TENANTTABLE, Constants.CASSANDRAKEYSPACE, "tenantid='" + tenant + "'");
			List<Row> rows = resultSet.all();
			for (Row row : rows) {
				keySpace = row.getString(Constants.CASSANDRAKEYSPACE);
			}
		} else {
		}
		return keySpace;
	}
	
}