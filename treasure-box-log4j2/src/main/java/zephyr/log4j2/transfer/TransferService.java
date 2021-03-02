package zephyr.log4j2.transfer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TransferService {

    public boolean transfer(long amount) {
        log.info("transfer money...");
        return true;
    }

    abstract protected void beforeTransfer(long amount);

    abstract protected void afterTransfer(long amount, boolean outcome);
}
