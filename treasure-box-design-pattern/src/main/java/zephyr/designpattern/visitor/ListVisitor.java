package zephyr.designpattern.visitor;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class ListVisitor extends Visitor {

    private String currentDir = "";

    @Override
    public void visit(File file) {
        log.info("{}/{}", currentDir, file);
    }

    @Override
    public void visit(Directory directory) {
        log.info("{}/{}", currentDir, directory);
        String saveDir = currentDir;
        currentDir = currentDir + "/" + directory.getName();
        final Iterator<Entry> iterator = directory.iterator();
        while (iterator.hasNext()) {
            iterator.next().accept(this);
        }
        currentDir = saveDir;
    }
}
