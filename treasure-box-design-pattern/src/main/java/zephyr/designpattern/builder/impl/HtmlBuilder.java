package zephyr.designpattern.builder.impl;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class HtmlBuilder extends LimitCallOrderBuilder {

    private String fileName;
    private PrintWriter writer;

    @Override
    public void buildTitle(String title) {
        fileName = title + ".html";
        try {
            writer = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        writer.println("<html><head><title>" + title + "</title></head><body>");
        writer.println("<h1>" + title + "</h1>");
    }

    @Override
    public void buildString(String string) {
        writer.println("<p>" + string + "</p>");
    }

    @Override
    public void buildItems(String[] items) {
        writer.println("<ul>");
        for (String item : items) {
            writer.println("<li>" + item + "</li>");
        }
        writer.println("</ul>");
    }

    @Override
    public void buildDone() {
        writer.println("</body></html>");
        writer.close();
    }

    public String getResult() {
        return fileName;
    }
}
