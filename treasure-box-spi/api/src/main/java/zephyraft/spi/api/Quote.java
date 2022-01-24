package zephyraft.spi.api;

import java.time.LocalDate;

public class Quote {
    private final String currency;
    private final LocalDate date;

    public Quote(String currency, LocalDate date) {
        this.currency = currency;
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "currency='" + currency + '\'' +
                ", date=" + date +
                '}';
    }
}
