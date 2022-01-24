package zephyraft.spi.impl;

import zephyraft.spi.api.Quote;
import zephyraft.spi.api.QuoteManager;

import java.time.LocalDate;
import java.util.List;

public class YahhoQuoteManagerImpl implements QuoteManager {
    @Override
    public List<Quote> getQuotes(String baseCurrency, LocalDate date) {
        // fetch from Yahoo API
        return List.of(new Quote("CNY", LocalDate.now()));
    }
}
