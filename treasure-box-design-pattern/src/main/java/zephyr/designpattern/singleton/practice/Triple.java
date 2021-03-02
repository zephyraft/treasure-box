package zephyr.designpattern.singleton.practice;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Triple {

    private static final List<Triple> tripleInstance = Lists.newArrayList(new Triple(), new Triple(), new Triple());

    private Triple() {
    }

    public static Triple getInstance(int id) {
        return tripleInstance.get(id);
    }

    public static void main(String[] args) {
        Triple instance1 = Triple.getInstance(0);
        Triple instance2 = Triple.getInstance(1);
        Triple instance3 = Triple.getInstance(2);

        log.info("{}", instance1 == instance2);
        log.info("{}", instance2 == instance3);
        log.info("{}", instance1 == instance3);
    }
}
