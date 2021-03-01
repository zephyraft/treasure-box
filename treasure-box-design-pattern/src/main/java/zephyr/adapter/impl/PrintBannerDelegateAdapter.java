package zephyr.adapter.impl;

import zephyr.adapter.Banner;
import zephyr.adapter.Print;

/**
 * 使用委托的适配器
 */
public class PrintBannerDelegateAdapter implements Print {

    private Banner banner;

    public PrintBannerDelegateAdapter(String string) {
        this.banner = new Banner(string);
    }

    @Override
    public void printWeak() {
        banner.showWithParen();
    }

    @Override
    public void printStrong() {
        banner.showWithAster();
    }
}
