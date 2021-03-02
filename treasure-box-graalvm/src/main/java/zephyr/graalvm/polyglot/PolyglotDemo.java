package zephyr.graalvm.polyglot;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
public class PolyglotDemo {

    public static final String JS = "js";
    // polyglot线程不安全，多线程访问时需要每个线程单独创建一份
    private static Context polyglot = null;

    static {
        try {
            polyglot = Context.newBuilder()
                    // 配置访问控制
                    .allowHostClassLookup(c -> c.equals("java.math.BigDecimal"))
                    .allowHostAccess(
                            HostAccess.newBuilder()
                                    .allowAccessAnnotatedBy(HostAccess.Export.class)
                                    .allowAccess(BigDecimal.class.getMethod("valueOf", long.class))
                                    .allowAccess(BigDecimal.class.getMethod("pow", int.class))
                                    .build()
                    )
                    .build();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // eval() 解析脚本
    // execute() 执行函数

    public static void main(String[] args) {
        log.info("值: {}",
                polyglot.eval(JS, "42")
                        .asInt()
        );

        log.info("语句:");
        polyglot.eval(JS, "console.log('Hello JavaScript!');");

        log.info("通过数组下标访问对象: {}",
                polyglot.eval(JS, "[1,2,42,4]")
                        .getArrayElement(2)
                        .asInt()
        );

        log.info("通过对象属性名获取值: {}",
                polyglot.eval(JS, "({id: 1, text: '2'})")
                        .getMember("id")
                        .asInt()
        );

        Value parse = polyglot.eval(JS, "JSON.parse");
        Value stringify = polyglot.eval(JS, "JSON.stringify");
        Value result = stringify.execute(parse.execute("{\"GraalVM\":{\"description\":\"Language Abstraction Platform\",\"supports\":[\"combining languages\",\"embedding languages\",\"creating native images\"],\"languages\": [\"Java\",\"JavaScript\",\"Node.js\", \"Python\", \"Ruby\",\"R\",\"LLVM\"]}}"), null, 2);
        log.info("函数: {}", result);

        log.info("Functions: {}", polyglot.eval(JS, "x => x + 1").execute(40).asInt());

        polyglot.getBindings("js").putMember("javaObj", new MyClass());
        log.info("脚本访问主语言对象: {}", polyglot.eval("js", "javaObj.id").asInt());

        log.info("脚本语言查找主语言对象: {}",
                polyglot.eval("js",
                        "var BigDecimal = Java.type('java.math.BigDecimal');" +
                                "BigDecimal.valueOf(10).pow(20)")
                        .asHostObject()
                        .toString());

        List<Object> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ProxyArray proxyArray = ProxyArray.fromList(list);
        log.info("ProxyArray: {}", polyglot.eval(JS, "it => it[2]").execute(proxyArray).asString());

        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("text", 2);
        ProxyObject proxyObject = ProxyObject.fromMap(map);
        log.info("ProxyObject: {}", polyglot.eval(JS, "it => it.id").execute(proxyObject).asInt());
    }

    public static class MyClass {
        @HostAccess.Export
        public int id = 42;
        public String text = "42";
        public int[] arr = new int[]{1, 42, 3};
        public Callable<Integer> ret42 = () -> 42;
    }
}
