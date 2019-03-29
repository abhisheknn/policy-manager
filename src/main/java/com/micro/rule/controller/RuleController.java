package com.micro.rule.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.micro.pojo.Rule;

public interface RuleController {
public ResponseEntity<Rule> createRule(Rule rule);
public ResponseEntity<List<Rule>> getRule(String tenant,String ruleName);
//public ResponseEntity<List<Rule>> getAllRules(String tenant);
public ResponseEntity<Rule> updateRule(Rule rule);
}
