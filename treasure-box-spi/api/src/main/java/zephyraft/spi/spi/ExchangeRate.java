package zephyraft.spi.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ExchangeRate {

    static final ServiceLoader<ExchangeRateProvider> loader = ServiceLoader
            .load(ExchangeRateProvider.class);

    public static Iterator<ExchangeRateProvider> providers(boolean refresh) {
        if (refresh) {
            loader.reload();
        }
        return loader.iterator();
    }

}
