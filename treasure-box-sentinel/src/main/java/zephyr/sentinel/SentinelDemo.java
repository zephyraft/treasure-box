package zephyr.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * java -Dahas.namespace=default  -Dproject.name=MyDemo  -Dahas.license=eeffd69d8577479cab321b1dd2b70a8f -jar ahas-sentinel-sdk-demo.jar
 */
@Slf4j
public class SentinelDemo {

    public static final String RESOURCE_NAME = "HelloWorld";

    public static void main(String[] args) {
        initFlowRules();

        while (true) {
            try (Entry entry = SphU.entry(RESOURCE_NAME)) {
                // 被保护的业务逻辑
                // do something here...
            } catch (BlockException ex) {
                // 资源访问阻止，被限流或被降级
                // 在此处进行相应的处理操作
            }
        }
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(RESOURCE_NAME);
        // set limit qps to 20
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
