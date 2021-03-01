package zephyr.factorymethod.idcard;

import lombok.extern.slf4j.Slf4j;
import zephyr.factorymethod.framework.Product;

import java.util.UUID;

@Slf4j
public class IDCard implements Product {

    private final String id;
    private final String owner;

    IDCard(String owner) {
        String uuid = UUID.randomUUID().toString();
        log.info("制作{}的ID卡：{}", owner, uuid);
        this.owner = owner;
        this.id = uuid;
    }

    @Override
    public void use() {
        log.info("使用{}的ID卡：{}", owner, id);
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }
}
