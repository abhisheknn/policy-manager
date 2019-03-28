package com.micro.rest.rule.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.micro.rest.rule.Rule;

public interface RuleController {
public ResponseEntity<Rule> createRule(Rule rule);
public ResponseEntity<List<Rule>> getRule(String tenant,String ruleName);
//public ResponseEntity<List<Rule>> getAllRules(String tenant);
public ResponseEntity<Rule> updateRule(String tenant,String ruleName,Rule rule);
}
