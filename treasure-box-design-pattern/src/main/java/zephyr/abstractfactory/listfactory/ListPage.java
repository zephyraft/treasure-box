package zephyr.abstractfactory.listfactory;

import zephyr.abstractfactory.factory.Item;
import zephyr.abstractfactory.factory.Page;

public class ListPage extends Page {

    public ListPage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html><head><title>").append(title).append("</title></head>\n");
        stringBuilder.append("<body>\n");
        stringBuilder.append("<h1>").append(title).append("</h1>\n");
        stringBuilder.append("<ul>\n");
        for (Item item : content) {
            stringBuilder.append(item.makeHTML());
        }
        stringBuilder.append("</ul>\n");
        stringBuilder.append("<hr><address>").append(author).append("</address>");
        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }
}
