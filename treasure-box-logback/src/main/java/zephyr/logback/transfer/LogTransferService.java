package zephyr.logback.transfer;

import lombok.extern.slf4j.Slf4j;

// 可以用aop代替
@Slf4j
public class LogTransferService extends TransferService {

    @Override
    public boolean transfer(long amount) {
        beforeTransfer(amount);
        boolean outcome = super.transfer(amount);
        afterTransfer(amount, outcome);
        return outcome;
    }

    @Override
    protected void beforeTransfer(long amount) {
        log.info("准备交易，金额为{}$.", amount);
    }

    @Override
    protected void afterTransfer(long amount, boolean outcome) {
        log.info("{}$的交易是否完成? {}.", amount, outcome);
    }
}
