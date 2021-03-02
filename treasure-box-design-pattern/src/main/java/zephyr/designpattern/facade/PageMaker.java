package zephyr.designpattern.facade;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PageMaker {

    private PageMaker() {
    }

    public static void makeWelcomePage(String mailAddr, String filename) throws IOException {
        Properties mailProp = Database.getProperties("maildata");
        String username = mailProp.getProperty(mailAddr);
        HtmlWriter writer = new HtmlWriter(new FileWriter(filename));
        writer.title("Welcome to" + username + "'s page!");
        writer.paragraph("Welcome to" + username + "'s page.");
        writer.paragraph("waiting for your email!");
        writer.mailto(mailAddr, username);
        writer.close();
        System.out.println(filename + " is created for " + mailAddr + " (" + username + ")");
    }
}
