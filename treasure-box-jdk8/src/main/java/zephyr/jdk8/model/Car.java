package zephyr.jdk8.model;

import java.util.Optional;

/**
 * 车
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
