package zephyr.netty.time;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;


@AllArgsConstructor
@Getter
public class UnixTime {

    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    @Override
    public String toString() {
        return new Date((getValue() - 2208988800L) * 1000L).toString();
    }
}
