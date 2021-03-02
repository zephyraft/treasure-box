package zephyr.designpattern.flyweight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BigChar {
    private char charName;
    private String fontData;

    public BigChar(char charName) {
        try {
            this.charName = charName;
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("big" + charName + ".txt")));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            this.fontData = stringBuilder.toString();
        } catch (IOException e) {
            this.fontData = charName + "?";
        }
    }

    public void print() {
        System.out.println(fontData);
    }
}
