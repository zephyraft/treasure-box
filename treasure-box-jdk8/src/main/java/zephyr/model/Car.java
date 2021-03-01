package zephyr.model;

import java.util.Optional;

/**
 * 车
 * Created by zephyr on 2018/12/20.
 */
public class Car {
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }
}
