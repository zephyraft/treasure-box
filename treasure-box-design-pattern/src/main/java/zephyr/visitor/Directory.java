package zephyr.visitor;

import zephyr.composite.FileTreatmentException;

import java.util.ArrayList;
import java.util.Iterator;

public class Directory extends Entry {
    private final String name;
    private final ArrayList<Entry> directory = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        int size = 0;
        for (Entry entry : directory) {
            size += entry.getSize();
        }
        return size;
    }

    @Override
    public Entry add(Entry entry) {
        directory.add(entry);
        return this;
    }

    @Override
    public Iterator<Entry> iterator() throws FileTreatmentException {
        return directory.iterator();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
