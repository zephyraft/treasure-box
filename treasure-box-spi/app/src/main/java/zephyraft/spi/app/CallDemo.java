package zephyraft.spi.app;

import zephyraft.spi.spi.ExchangeRate;

import java.time.LocalDate;

public class CallDemo {
    public static void main(String[] args) {
        ExchangeRate.providers(false).forEachRemaining(provider ->
                provider.create()
                        .getQuotes("CNY", LocalDate.now())
                        .forEach(System.out::println));
    }
}
