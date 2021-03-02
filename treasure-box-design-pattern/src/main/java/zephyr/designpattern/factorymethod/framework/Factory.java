package zephyr.designpattern.factorymethod.framework;

public interface Factory<T extends Product> {

    T createProduct(String owner);

    void registerProduct(T product);

    default T create(String owner){
        final T product = createProduct(owner);
        registerProduct(product);
        return product;
    }

}
