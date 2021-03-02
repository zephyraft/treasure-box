package zephyr.designpattern.builder.impl;

public class TextBuilder extends LimitCallOrderBuilder {

    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void buildTitle(String title) {
        stringBuilder.append("\n==============================\n");
        stringBuilder.append("[").append(title).append("]\n\n");
    }

    @Override
    public void buildString(String string) {
        stringBuilder.append("*").append(string).append("\n\n");
    }

    @Override
    public void buildItems(String[] items) {
        for (String item : items) {
            stringBuilder.append(" .").append(item).append("\n");
        }
        stringBuilder.append("\n");
    }

    @Override
    public void buildDone() {
        stringBuilder.append("==============================\n");
    }

    public String getResult() {
        return stringBuilder.toString();
    }
}
