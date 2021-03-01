package zephyr.compiler;

/**
 * -XX:-UseJVMCICompiler 禁用graalvm编译器，使用原生编译器
 * <p>
 * graalvm执行下一段代码时，拥有更好的性能
 * ~3000ms -> ~1500ms
 * https://www.graalvm.org/examples/java-performance-examples/
 */
public class CountUppercase {

    static final int ITERATIONS = Math.max(Integer.getInteger("iterations", 1), 1);

    public static void main(String[] args) {
        String sentence = String.join(" ", args);
        for (int iter = 0; iter < ITERATIONS; iter++) {
            if (ITERATIONS != 1) System.out.println("-- iteration " + (iter + 1) + " --");
            long total = 0, start = System.currentTimeMillis(), last = start;
            for (int i = 1; i < 10_000_000; i++) {
                total += sentence.chars().filter(Character::isUpperCase).count();
                if (i % 1_000_000 == 0) {
                    long now = System.currentTimeMillis();
                    System.out.printf("%d (%d ms)%n", i / 1_000_000, now - last);
                    last = now;
                }
            }
            System.out.printf("total: %d (%d ms)%n", total, System.currentTimeMillis() - start);
        }
    }

}
