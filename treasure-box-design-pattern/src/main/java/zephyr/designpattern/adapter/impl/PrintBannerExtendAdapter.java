package zephyr.designpattern.adapter.impl;

import zephyr.designpattern.adapter.Banner;
import zephyr.designpattern.adapter.Print;

/**
 * 使用继承的适配器
 */
public class PrintBannerExtendAdapter extends Banner implements Print {
    public PrintBannerExtendAdapter(String string) {
        super(string);
    }

    @Override
    public void printWeak() {
        showWithParen();
    }

    @Override
    public void printStrong() {
        showWithAster();
    }
}
