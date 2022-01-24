package zephyraft.spi.impl;

import zephyraft.spi.api.QuoteManager;
import zephyraft.spi.spi.ExchangeRateProvider;

public class YahooFinanceExchangeRateProvider implements ExchangeRateProvider {

    @Override
    public QuoteManager create() {
        return new YahhoQuoteManagerImpl();
    }
}
