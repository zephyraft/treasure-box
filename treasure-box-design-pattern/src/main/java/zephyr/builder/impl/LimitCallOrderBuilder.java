package zephyr.builder.impl;

import zephyr.builder.Builder;

public abstract class LimitCallOrderBuilder implements Builder {

    /**
     * 确保在调用其他方法前有且只调用过一次makeTitle
     */
    private boolean initialized = false;

    @Override
    public void makeTitle(String title) {
        if (!initialized) {
            buildTitle(title);
            initialized = true;
        }
    }

    @Override
    public void makeString(String string) {
        if (initialized) {
            buildString(string);
        }
    }

    @Override
    public void makeItems(String[] items) {
        if (initialized) {
            buildItems(items);
        }
    }

    @Override
    public void close() {
        if (initialized) {
            buildDone();
        }
    }

    abstract void buildTitle(String title);
    abstract void buildString(String string);
    abstract void buildItems(String[] items);
    abstract void buildDone();
}
