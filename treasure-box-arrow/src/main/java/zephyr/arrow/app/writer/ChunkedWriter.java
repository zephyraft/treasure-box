package zephyr.arrow.app.writer;

import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.dictionary.DictionaryProvider;
import org.apache.arrow.vector.ipc.ArrowFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zephyr.arrow.app.schema.AppSchemas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChunkedWriter<T> {

    private static final Logger log = LoggerFactory.getLogger(ChunkedWriter.class);

    private final int chunkSize;
    private final Vectorizer<T> vectorizer;

    public ChunkedWriter(int chunkSize, Vectorizer<T> vectorizer) {
        this.chunkSize = chunkSize;
        this.vectorizer = vectorizer;
    }

    public void write(File file, T[] values) throws IOException {
        DictionaryProvider.MapDictionaryProvider dictProvider = new DictionaryProvider.MapDictionaryProvider();

        try (RootAllocator allocator = new RootAllocator();
             VectorSchemaRoot schemaRoot = VectorSchemaRoot.create(AppSchemas.personSchema(), allocator);
             FileOutputStream fd = new FileOutputStream(file);
             ArrowFileWriter fileWriter = new ArrowFileWriter(schemaRoot, dictProvider, fd.getChannel())) {

            log.info("Start writing");
            fileWriter.start();

            int index = 0;
            while (index < values.length) {
                schemaRoot.allocateNew();
                int chunkIndex = 0;
                while (chunkIndex < chunkSize && index + chunkIndex < values.length) {
                    vectorizer.vectorize(values[index + chunkIndex], chunkIndex, schemaRoot);
                    chunkIndex++;
                }
                schemaRoot.setRowCount(chunkIndex);
                log.info("Filled chunk with {} items; {} items written", chunkIndex, index + chunkIndex);
                fileWriter.writeBatch();
                log.info("Chunk written");

                index += chunkIndex;
                schemaRoot.clear();
            }

            log.info("Writing done");
            fileWriter.end();
        }
    }

    @FunctionalInterface
    public interface Vectorizer<T> {
        void vectorize(T value, int index, VectorSchemaRoot batch);
    }
}
