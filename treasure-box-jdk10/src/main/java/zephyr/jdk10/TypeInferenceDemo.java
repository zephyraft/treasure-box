package zephyr.jdk10;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TypeInferenceDemo {
    public static void main(String[] args) {
        var list = new ArrayList<String>();
        list.add("1");

        var stream = list.stream();
        final List<String> hello = stream.map(t -> t + "s").collect(Collectors.toList());
        System.out.println(hello);
    }
}
