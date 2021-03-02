package zephyr.designpattern.adapter.practice;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AdapterPracticeDemo {

    public static void main(String[] args) {
        FileIO fileIO = new FileProperties();
        try {
            fileIO.readFromFile("file.txt");
            fileIO.setValue("year", "2004");
            fileIO.setValue("month", "4");
            fileIO.setValue("day", "21");
            fileIO.writeToFile("newfile.txt");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
