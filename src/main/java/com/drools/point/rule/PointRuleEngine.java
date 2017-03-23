package com.drools.point.rule;

import com.drools.point.domain.Point;

/**
 * Created by zhongjing on 2016/1/8 0008.
 */
public interface PointRuleEngine {
    /**
     * 初始化规则引擎
     */
    public void initEngine();

    /**
     * 刷新规则引擎的规则
     */
    public void refreshEnginRule();

    /**
     * 执行规则引擎
     *
     * @param point 积分Fact
     */
    public void executeRuleEngine(final Point point);
}
