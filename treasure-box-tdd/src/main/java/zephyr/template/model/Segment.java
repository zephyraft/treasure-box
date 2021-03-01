package zephyr.template.model;

import java.util.Map;

/**
 * Created by zephyr on 2019-09-29.
 */
public interface Segment {

    String evaluate(Map<String, String> variables);

}
