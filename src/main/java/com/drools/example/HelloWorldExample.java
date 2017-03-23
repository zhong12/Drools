package com.drools.example;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.drools.rule.Package;

import java.io.FileReader;
import java.io.Reader;

/**
 * Created by zhongjing on 2015/12/31 0031.
 */
public class HelloWorldExample {
    public static void main(String[] args) throws Exception {
        String filePath = "D:\\work\\gitProjects\\drools_example\\src\\main\\resources\\HelloWorld.drl";
        final Reader source = new FileReader(filePath);
        final PackageBuilder builder = new PackageBuilder();
        builder.addPackageFromDrl(source);
        if (builder.hasErrors()) {
            System.out.println(builder.getErrors().toString());
            throw new RuntimeException("HelloWorld.drl Unable to compile.");
        }

        final Package pkg = builder.getPackage();
        final RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(pkg);

        final StatefulSession session = ruleBase.newStatefulSession();
        session.addEventListener(new DebugAgendaEventListener());
        session.addEventListener(new DebugWorkingMemoryEventListener());

        final Message message = new Message();
        message.setMessage("Hello World 123");
        message.setStatus(0);
        session.insert(message);
        session.fireAllRules();
        session.dispose();
    }

    public static class Message {
        public static final int HELLO = 0;
        public static final int GOODBYE = 1;
        private String message;
        private int status;
        public String getMessage() {
            return this.message;
        }
        public void setMessage(final String message) {
            this.message = message;
        }
        public int getStatus() {
            return this.status;
        }
        public void setStatus(final int status) {
            this.status = status;
        }
    }
}
