package zephyr.grokkingalgorithms;

import java.util.*;

/**
 * 广度有限搜索 O(V+E) O(顶点数+边数)
 * 借助队列实现
 * 解决非加权图最短路径问题
 * 判断路径是否可达
 */
public class BFS {

    public static void main(String[] args) {
        Node root = new Node("ZEPHYR",
                new Node[]{
                        new Node("BOB", new Node[]{new Node("ANUJ", null), new Node("PEGGY", null)}),
                        new Node("ALICE", new Node[]{new Node("PEGGY", new Node[]{new Node("ALICE", null)})}),
                        new Node("CLAIRE", new Node[]{new Node("THOM", null), new Node("JONNY", null)}),
                });

        System.out.println(breadthFirstSearchAccessible(root, "THOM"));
        System.out.println(breadthFirstSearchAccessible(root, "HELLO"));
        System.out.println(breadthFirstSearchAccessible(root, "ALICE"));
    }

    /**
     * 判断是否可达
     *
     * @param root 广度搜索起点
     * @return 是否可达
     */
    private static boolean breadthFirstSearchAccessible(Node root, String value) {
        // 搜索队列
        Deque<Node> queue = new LinkedList<>();
        queue.addLast(root);

        // 需要记录已搜索过的节点，避免无限循环
        List<Node> searched = new ArrayList<>();

        while (!queue.isEmpty()) {
            final Node node = queue.removeFirst();
            if (!searched.contains(node)) {
                searched.add(node);
                if (node.value.equals(value)) {
                    return true;
                } else {
                    if (node.child != null) {
                        for (Node child : node.child) {
                            queue.addLast(child);
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 图节点
     */
    private static class Node {
        String value;
        Node[] child;

        public Node(String value, Node[] child) {
            this.value = value;
            this.child = child;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

}
