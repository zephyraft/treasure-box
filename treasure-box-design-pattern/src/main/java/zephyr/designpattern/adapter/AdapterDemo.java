package zephyr.designpattern.adapter;

import zephyr.designpattern.adapter.impl.PrintBannerDelegateAdapter;
import zephyr.designpattern.adapter.impl.PrintBannerExtendAdapter;

/**
 * 适配器模式（也被称为Wrapper模式）
 * 类适配器模式（使用继承）
 * 对象适配器模式（使用委托）
 *
 * 适配器模式 - 4个角色
 * Target：对象，负责定义所需的方法
 * @see zephyr.designpattern.adapter.Print
 * Client：请求者，负责使用对象定义的方法
 * @see zephyr.designpattern.adapter.AdapterDemo#main(String[])
 * Adaptee：被适配者
 * @see zephyr.designpattern.adapter.Banner
 * Adapter：适配器
 * @see zephyr.designpattern.adapter.impl.PrintBannerDelegateAdapter
 * @see zephyr.designpattern.adapter.impl.PrintBannerExtendAdapter
 *
 * 存在现有的类，被充分测试过，需要复用，但没有现成的代码时，或是不愿意修改现有的类，避免引入bug时，
 * 可以在完全不改变现有代码的前提下，使现有的代码适配新的接口
 * 版本升级若需要兼容旧版本，可以使新版本的类扮演被适配者，编写适配旧版本的适配器
 * 功能完全不同的类是无法适配的
 *
 * 相关设计模式：
 * Bridge
 * Decorator
 *
 */
public class AdapterDemo {

    public static void main(String[] args) {
        Print extendAdapter = new PrintBannerExtendAdapter("Hello");
        extendAdapter.printWeak();
        extendAdapter.printStrong();


        Print delegateAdapter = new PrintBannerDelegateAdapter("Hello");
        delegateAdapter.printWeak();
        delegateAdapter.printStrong();
    }

}
