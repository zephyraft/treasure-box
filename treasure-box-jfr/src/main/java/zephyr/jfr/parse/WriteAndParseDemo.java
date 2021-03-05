package zephyr.jfr.parse;

import jdk.jfr.*;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteAndParseDemo {

    @Name("com.oracle.Hello")
    @Label("Hello World!")
    static class Hello extends Event {
        @Label("Greeting")
        String greeting;
    }

    @Name("com.oracle.Message")
    @Label("Message")
    static class Message extends Event {
        @Label("Text")
        String text;
    }

    public static void main(String... args) throws IOException {
        try (Recording r = new Recording()) {
            r.start();
            for (int i = 0; i < 3; i++) {
                Message messageEvent = new Message();
                messageEvent.begin();
                messageEvent.text = "message " + i;
                messageEvent.commit();

                Hello helloEvent = new Hello();
                helloEvent.begin();
                helloEvent.greeting = "hello " + i;
                helloEvent.commit();
            }
            r.stop();
            Path file = Files.createTempFile("recording", ".jfr");
            r.dump(file);


            try (RecordingFile recordingFile = new RecordingFile(file)) {
                System.out.println("Reading events one by one");
                System.out.println("=========================");
                while (recordingFile.hasMoreEvents()) {
                    RecordedEvent e = recordingFile.readEvent();
                    String eventName = e.getEventType().getName();
                    System.out.println("Name: " + eventName);
                }
                System.out.println();
                System.out.println("List of registered event types");
                System.out.println("==============================");
                for (EventType eventType : recordingFile.readEventTypes())
                {
                    System.out.println(eventType.getName());
                }
            }
            System.out.println();

            System.out.println("Reading all events at once");
            System.out.println("==========================");

            for (RecordedEvent e : RecordingFile.readAllEvents(file)) {
                String eventName = e.getEventType().getName();
                System.out.println("Name: " + eventName);
            }
            System.out.println();
        }
    }
}
