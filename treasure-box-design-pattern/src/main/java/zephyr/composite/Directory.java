package zephyr.composite;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
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
    protected void printList(String prefix) {
        log.info("{}/{}", prefix, this);
        for (Entry entry : directory) {
            entry.printList(prefix + "/" + name);
        }
    }
}
