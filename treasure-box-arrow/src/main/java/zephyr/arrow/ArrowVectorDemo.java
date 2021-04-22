package zephyr.arrow;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.util.TransferPair;
import zephyr.arrow.unsafe.DisableUnsafeWarning;

public class ArrowVectorDemo {

    public static void main(String[] args) {
        DisableUnsafeWarning.disableWarning();

        try (
                BufferAllocator allocator = new RootAllocator();
                IntVector vector = new IntVector("intVector", allocator);
        ) {
            vector.allocateNew(); // 申请内存
            int count = 10;
            for (int i = 0; i < count; i++) {
                vector.setSafe(i, i); // 设置向量值
            }
            vector.setValueCount(count); // 设置值计数，在此步骤之后，向量进入不可变状态
            System.out.println(vector);

            // 创建切片
            TransferPair tp = vector.getTransferPair(allocator);
            tp.splitAndTransfer(0, 5);
            IntVector sliced = (IntVector) tp.getTo();

            System.out.println(sliced);
            sliced.close(); // 一定要记得手动释放内存，否则会导致内存泄露
        }
    }
}
