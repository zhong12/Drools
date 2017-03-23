package com.drools.point.rule.impl;

import com.drools.point.domain.Point;
import com.drools.point.factory.RuleBaseFactory;
import com.drools.point.rule.PointRuleEngine;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.drools.spi.Activation;
import org.drools.spi.AgendaFilter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjing on 2016/1/8 0008.
 * 规则接口实现类
 */
public class PointRuleEngineImpl implements PointRuleEngine {

    private RuleBase ruleBase;

    @Override
    public void initEngine() {
        System.setProperty("drools.dateformat", "yyyy-mm-dd HH:mm:ss");
        ruleBase = RuleBaseFactory.getRuleBase();
        try {
            PackageBuilder builder = getPackageBuilderFromDrlFile();
            ruleBase.addPackages(builder.getPackages());
        } catch (DroolsParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void refreshEnginRule() {
        ruleBase = RuleBaseFactory.getRuleBase();
        Package[] packages = ruleBase.getPackages();
        for (Package pa : packages) {
            ruleBase.removePackage(pa.getName());
        }
    }

    @Override
    public void executeRuleEngine(Point point) {
        if (null == ruleBase.getPackages() || 0 == ruleBase.getPackages().length) {
            return;
        }

        StatefulSession session = ruleBase.newStatefulSession();
        session.insert(point);

        session.fireAllRules(new AgendaFilter() {
            public boolean accept(Activation activation) {
                return !activation.getRule().getName().contains("_test");
            }
        });

        session.dispose();
    }


    /**
     * 从drl规则文件中读取规则
     *
     * @return
     * @throws Exception
     */
    private PackageBuilder getPackageBuilderFromDrlFile() throws Exception {
        /**
         * 获取测试脚本文件
         */
        List<String> drlFilePath = getTestDrilFile();

        /**
         * 装载测试脚本文件
         */
        List<Reader> readers = readRuleFromDrlFile(drlFilePath);

        PackageBuilder packageBuilder = new PackageBuilder();

        for (Reader reader : readers) {
            packageBuilder.addPackageFromDrl(reader);
        }

        /**
         * 检查脚本是否有问题
         */
        if (packageBuilder.hasErrors()) {
            throw new Exception(packageBuilder.getErrors().toString());
        }

        return packageBuilder;
    }


    /**
     * 读取规则文件并封装成Reader
     *
     * @param drlFilePath 脚本文件路径
     * @return
     * @throws java.io.FileNotFoundException
     */
    private List<Reader> readRuleFromDrlFile(List<String> drlFilePath) throws FileNotFoundException {
        if (null == drlFilePath || 0 == drlFilePath.size()) {
            return null;
        }

        List<Reader> readers = new ArrayList<Reader>();

        for (String ruleFilePath : drlFilePath) {
            readers.add(new FileReader(new File(ruleFilePath)));
        }

        return readers;
    }


    private List<String> getTestDrilFile() {
        List<String> drlFilePath = new ArrayList<String>();
        drlFilePath.add("src/main/resources/point/subpoint.drl");
        drlFilePath.add("src/main/resources/point/addpoint.drl");
        return drlFilePath;
    }
}
