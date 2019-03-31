package com.micro.policy.common;

public class Constants {

	public static final String CASSANDRA_HOST=System.getenv("CASSANDRA_HOST");
	public static final String CASSANDRA_PORT=System.getenv("CASSANDRA_PORT");
	public static final String DOCKERKEYSPACE="dockerxconf";//(System.getenv("DOCKERXCONFKEYSPACE")!=null)?System.getenv("DOCKERXCONFKEYSPACE"): "dockerx";
	public static final String RULETABLE = "rule";
	public static final String TENANTTABLE = "tenant";
	public static final String DOCKERXALL = "dockerx_all";
	public static final String CASSANDRAKEYSPACE = "cassandrakeyspace";
	public static final String POLICYTABLE = "policy";
	public static final String RULETYPE = "ruleType";

}
