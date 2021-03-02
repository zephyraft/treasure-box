package zephyr.tdd.template.model;

import java.util.Map;


public interface Segment {

    String evaluate(Map<String, String> variables);

}
