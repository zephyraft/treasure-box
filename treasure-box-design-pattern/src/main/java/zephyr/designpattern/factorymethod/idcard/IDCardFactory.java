package zephyr.designpattern.factorymethod.idcard;

import zephyr.designpattern.factorymethod.framework.Factory;

import java.util.HashMap;
import java.util.Map;

public class IDCardFactory implements Factory<IDCard> {

    private final Map<String, String> idOwnerMap = new HashMap<>();

    @Override
    public IDCard createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    public void registerProduct(IDCard product) {
        idOwnerMap.put(product.getId(), product.getOwner());
    }

    public Map<String, String> getIdOwnerMap() {
        return idOwnerMap;
    }
}
