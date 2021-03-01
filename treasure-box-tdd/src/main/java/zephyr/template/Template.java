package zephyr.template;

import zephyr.template.model.Segment;
import zephyr.template.parse.TemplateParse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zephyr on 2019-09-29.
 */
public class Template {

    private Map<String, String> variables;
    private String templateText;

    public Template(String templateText) {
        this.variables = new HashMap<>();
        this.templateText = templateText;
    }

    public void set(String name, String value) {
        variables.put(name, value);
    }

    public String evaluate() {
        TemplateParse parser = new TemplateParse();
        List<Segment> segments = parser.parseSegments(templateText);
        return concatenate(segments);
    }

    private String concatenate(List<Segment> segments) {
        StringBuilder result = new StringBuilder();
        for (Segment segment : segments) {
            result.append(segment.evaluate(variables));
        }
        return result.toString();
    }

}
