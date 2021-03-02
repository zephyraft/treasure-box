package zephyr.designpattern.builder;

import lombok.extern.slf4j.Slf4j;
import zephyr.designpattern.builder.impl.HtmlBuilder;
import zephyr.designpattern.builder.impl.TextBuilder;

@Slf4j
public class BuilderDemo {

    public static void main(String[] args) {
        TextBuilder textBuilder = new TextBuilder();
        Director director1 = new Director(textBuilder);
        director1.construct();
        log.info(textBuilder.getResult());


        HtmlBuilder htmlBuilder = new HtmlBuilder();
        Director director2 = new Director(htmlBuilder);
        director2.construct();
        log.info(htmlBuilder.getResult());
    }
}
