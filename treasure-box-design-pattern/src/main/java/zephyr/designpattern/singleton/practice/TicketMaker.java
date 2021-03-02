package zephyr.designpattern.singleton.practice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketMaker {

    private static final TicketMaker singleton = new TicketMaker();
    private int ticket = 1000;

    private TicketMaker() {
    }

    public static TicketMaker getInstance() {
        return singleton;
    }

    public static void main(String[] args) {
        log.info("{}", TicketMaker.getInstance().getNextTicketNumber());
        log.info("{}", TicketMaker.getInstance().getNextTicketNumber());
    }

    public int getNextTicketNumber(){
        return ticket++;
    }
}
