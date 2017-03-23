package com.drools.point.test;

import com.drools.point.domain.Point;
import com.drools.point.rule.PointRuleEngine;
import com.drools.point.rule.impl.PointRuleEngineImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhongjing on 2016/1/8 0008.
 */
public class PointTest {
    public static void main(String[] args) throws IOException {
        PointRuleEngine pointRuleEngine = new PointRuleEngineImpl();
        while (true) {
            InputStream is = System.in;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            System.out.println("输入：");
            String input = br.readLine();

            if (null != input && "s".equals(input)) {
                System.out.println("初始化规则引擎...");
                pointRuleEngine.initEngine();
                System.out.println("初始化规则引擎结束.");
            } else if ("e".equals(input)) {
                final Point point = new Point();
                point.setUserName("zj");
                point.setBackMoney(100d);
                point.setBuyMoney(500d);
                point.setBackNums(1);
                point.setBuyNums(5);
                point.setBillThisMonth(5);
                point.setBirthDay(true);
                point.setPoint(0l);

                pointRuleEngine.executeRuleEngine(point);

                System.out.println("执行完毕BillThisMonth：" + point.getBillThisMonth());
                System.out.println("执行完毕BuyMoney：" + point.getBuyMoney());
                System.out.println("执行完毕BuyNums：" + point.getBuyNums());

                System.out.println("执行完毕规则引擎决定发送积分：" + point.getPoint());
            } else if ("r".equals(input)) {
                System.out.println("刷新规则文件...");
                pointRuleEngine.refreshEnginRule();
                System.out.println("刷新规则文件结束.");
            }
        }
    }
}
