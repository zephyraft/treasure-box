package zephyr.log4j2.transfer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class LogRunnable implements Runnable {

    private final LogTransferService logTransferService;
    private final Transfer tx;

    public LogRunnable(Transfer tx) {
        this.tx = tx;
        logTransferService = new LogTransferService();
    }

    @Override
    public void run() {
        try (
                @SuppressWarnings("unused")
                MDC.MDCCloseable tranId = MDC.putCloseable("transaction.id", tx.getTransactionId());
                @SuppressWarnings("unused")
                MDC.MDCCloseable sender = MDC.putCloseable("transaction.sender", tx.getSender());
        ) {
            logTransferService.transfer(tx.getAmount());
        }
    }
}
