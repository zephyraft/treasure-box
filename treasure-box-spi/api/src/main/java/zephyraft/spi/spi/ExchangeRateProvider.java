package zephyraft.spi.spi;

import zephyraft.spi.api.QuoteManager;

public interface ExchangeRateProvider {
    QuoteManager create();
}
