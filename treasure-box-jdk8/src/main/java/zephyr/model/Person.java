package zephyr.model;

import java.util.Optional;

/**
 * 人
 * Created by zephyr on 2018/12/20.
 */
public class Person {
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }
}
