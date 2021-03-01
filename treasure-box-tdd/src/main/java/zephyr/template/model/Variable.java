package zephyr.template.model;

import zephyr.template.exception.MissingValueException;

import java.util.Map;

/**
 * Created by zephyr on 2019-09-29.
 */
public class Variable implements Segment {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Variable)) {
            return false;
        }
        return name.equals(((Variable) other).name);
    }

    @Override
    public String evaluate(Map<String, String> variables) {
        if (!variables.containsKey(name)) {
            throw new MissingValueException("No value for ${" + name + "}");
        }
        return variables.get(name);
    }
}
