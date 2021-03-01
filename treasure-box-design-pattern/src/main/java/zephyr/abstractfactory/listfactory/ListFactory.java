package zephyr.abstractfactory.listfactory;

import zephyr.abstractfactory.factory.Factory;
import zephyr.abstractfactory.factory.Link;
import zephyr.abstractfactory.factory.Page;
import zephyr.abstractfactory.factory.Tray;

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
