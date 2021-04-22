package zephyr.arrow;

import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.*;
import org.apache.arrow.vector.ipc.message.ArrowRecordBatch;
import org.apache.arrow.vector.types.pojo.Field;
import zephyr.arrow.unsafe.DisableUnsafeWarning;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ArrowVectorSchemaDemo {

    public static void main(String[] args) {
        DisableUnsafeWarning.disableWarning();

        try (
                RootAllocator allocator = new RootAllocator();
                BitVector bitVector = new BitVector("boolean", allocator);
                VarCharVector varCharVector = new VarCharVector("varchar", allocator);
        ) {
            bitVector.allocateNew();
            varCharVector.allocateNew();
            for (int i = 0; i < 10; i++) {
                bitVector.setSafe(i, i % 2 == 0 ? 0 : 1);
                varCharVector.setSafe(i, ("test" + i).getBytes(StandardCharsets.UTF_8));
            }
            bitVector.setValueCount(10);
            varCharVector.setValueCount(10);

            System.out.println(bitVector);
            System.out.println(varCharVector);

            List<Field> fields = Arrays.asList(bitVector.getField(), varCharVector.getField());
            List<FieldVector> vectors = Arrays.asList(bitVector, varCharVector);


            System.out.println(fields);
            System.out.println(vectors);
//            VectorSchemaRoot vectorSchemaRoot = new VectorSchemaRoot(fields, vectors);


            // create a VectorSchemaRoot root1 and convert its data into recordBatch
            VectorSchemaRoot root1 = new VectorSchemaRoot(fields, vectors);
            VectorUnloader unloader = new VectorUnloader(root1);
            ArrowRecordBatch recordBatch = unloader.getRecordBatch();
            System.out.println(recordBatch);

            // create a VectorSchemaRoot root2 and load the recordBatch
            VectorSchemaRoot root2 = VectorSchemaRoot.create(root1.getSchema(), allocator);
            VectorLoader loader = new VectorLoader(root2);
            loader.load(recordBatch);
            System.out.println(recordBatch);

            recordBatch.close();
            root1.close();
            root2.close();
        }
    }

}
