package zephyr.designpattern.abstractfactory.listfactory;

import zephyr.designpattern.abstractfactory.factory.Factory;
import zephyr.designpattern.abstractfactory.factory.Link;
import zephyr.designpattern.abstractfactory.factory.Page;
import zephyr.designpattern.abstractfactory.factory.Tray;

public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLink(caption, url);
    }

    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    public Page createPage(String title, String author) {
        return new ListPage(title, author);
    }
}
