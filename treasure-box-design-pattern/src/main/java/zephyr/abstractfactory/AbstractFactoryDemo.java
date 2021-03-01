package zephyr.abstractfactory;

import zephyr.abstractfactory.factory.Factory;
import zephyr.abstractfactory.factory.Link;
import zephyr.abstractfactory.factory.Page;
import zephyr.abstractfactory.factory.Tray;

public class AbstractFactoryDemo {

    public static void main(String[] args) {
        Factory factory = Factory.getFactory("zephyr.abstractfactory.listfactory.ListFactory");

        Link people = factory.createLink("人民日报", "http://www.people.com.cn");
        Link gmw = factory.createLink("光明日报", "http://www.gmw.cn");

        Link usYahoo = factory.createLink("Yahoo!", "http://www.yahoo.com");
        Link jpYahoo = factory.createLink("Yahoo!Japan", "http://www.yahoo.co.jp");
        Link excite = factory.createLink("Excite", "http://www.excite.com");
        Link google = factory.createLink("Google", "http://www.google.com");

        Tray trayNews = factory.createTray("日报");
        trayNews.add(people);
        trayNews.add(gmw);

        Tray trayYahoo = factory.createTray("Yahoo!");
        trayYahoo.add(usYahoo);
        trayYahoo.add(jpYahoo);

        Tray traySearch = factory.createTray("检索引擎");
        traySearch.add(excite);
        traySearch.add(google);

        Page page = factory.createPage("LinkPage", "zephyr");
        page.add(trayNews);
        page.add(traySearch);
        page.output();
    }

}
