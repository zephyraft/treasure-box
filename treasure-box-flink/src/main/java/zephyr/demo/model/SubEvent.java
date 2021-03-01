package zephyr.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class SubEvent extends Event implements Serializable {

    private double volume;

    public SubEvent() {
    }

    public SubEvent(int id, String name, double volume) {
        super(id, name);
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubEvent subEvent = (SubEvent) o;
        return Double.compare(subEvent.volume, volume) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }

    @Override
    public String toString() {
        return "SubEvent{" +
                "volume=" + volume +
                "} " + super.toString();
    }
}
