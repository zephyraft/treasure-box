package zephyr.guava;

import static com.google.common.base.Preconditions.*;

public class PreconditionsDemo {

    public static void main(String[] args) {
        // checkArgument(false); // IllegalArgumentException
        checkArgument(true); // IllegalArgumentException
        // checkNotNull(null); // NullPointerException
        checkNotNull(new Object()); // NullPointerException
        // final int i = -1;
        final int i = 1;
        checkArgument(i >= 0, "Argument was %s but expected nonnegative", i); // IllegalArgumentException
        // checkState(false); // IllegalStateException
        checkState(true); // IllegalStateException
        // checkElementIndex(-1, 2); // IndexOutOfBoundsException
        // checkElementIndex(3, 2); // IndexOutOfBoundsException
        // 大于等于0 小于size 即[0, size)
        checkElementIndex(1, 2); // IndexOutOfBoundsException
        // 大于等于0 小于等于size 在临界值不产生异常 即[0, size]
        checkPositionIndex(1, 2); // IndexOutOfBoundsException
        // 大于等于start 小于end 即[start, end)
        checkPositionIndexes(1, 2, 1);

    }

}
