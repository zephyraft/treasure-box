package zephyr.facade;

import java.io.IOException;

public class FacadeDemo {
    public static void main(String[] args) throws IOException {
        PageMaker.makeWelcomePage("hyuki@hyuki.com", "welcome.html");
    }
}
