package zephyr.jfr.parse;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ParseDemo {

    public static void main(String... args) throws IOException, URISyntaxException {
        Path file = Paths.get(ClassLoader.getSystemResource("myrecording.jfr").toURI());

        try (RecordingFile recordingFile = new RecordingFile(file)) {
            while (recordingFile.hasMoreEvents()) {
                RecordedEvent recordedEvent = recordingFile.readEvent();
                log.info(recordedEvent.toString());
            }

//            while (recordingFile.hasMoreEvents()) {
//                RecordedEvent e = recordingFile.readEvent();
//                String eventName = e.getEventType().getName();
//                System.out.println("Name: " + eventName);
//            }
//            System.out.println();
//            System.out.println("List of registered event types");
//            System.out.println("==============================");
//            for (EventType eventType : recordingFile.readEventTypes()) {
//                System.out.println(eventType.getName());
//            }
        }

//        System.out.println("Reading all events at once");
//        System.out.println("==========================");
//
//        for (RecordedEvent e : RecordingFile.readAllEvents(file)) {
//            String eventName = e.getEventType().getName();
//            System.out.println("Name: " + eventName);
//        }
//        System.out.println();
    }
}
