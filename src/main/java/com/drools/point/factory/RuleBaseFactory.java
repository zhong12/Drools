package com.drools.point.factory;

import org.drools.RuleBase;

/**
 * Created by zhongjing on 2016/1/8 0008.
 */
public class RuleBaseFactory {
    private static RuleBase ruleBase;

    public static RuleBase getRuleBase() {
        return null != ruleBase ? ruleBase : org.drools.RuleBaseFactory.newRuleBase();
    }
}
