package zephyr.adapter.impl;

import zephyr.adapter.Banner;
import zephyr.adapter.Print;

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
