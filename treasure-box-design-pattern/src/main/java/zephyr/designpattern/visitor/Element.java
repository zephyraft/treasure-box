package zephyr.designpattern.visitor;

public interface Element {
    void accept(Visitor v);
}
