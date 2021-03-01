package zephyr.grokkingalgorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 狄克斯特拉算法
 * 适用于计算有向无环加权图（不包含负权）的最短加权路径
 * 负权可用 贝尔曼-福德算法 Bellman-Ford
 */
public class Dijkstra {

    // 本示例的有向无环正加权图如下
    // START -- 6 --> A -- 1 --> END
    //   |            ^           |
    //   |            |           |
    //   |            3           |
    //   |            |           |
    //   |----- 2 --> B -- 5 -----|

    public static final String START = "START";
    public static final String END = "END";
    public static final String A = "A";
    public static final String B = "B";

    public static void main(String[] args) {
        // 存储图
        Map<String, Map<String, Integer>> graph = getGraphMap();
        // 开销表
        Map<String, Integer> costs = getCostMap();
        // 存储父节点（作为最终路径的记录）
        Map<String, String> parents = getParentMap();
        dijkstra(graph, costs, parents);

        // 打印路径
        System.out.println(parents);
        // 打印最小开销
        System.out.println(costs);
    }

    /**
     * 狄克斯特拉算法
     * @param graph 图的散列表 节点 - 节点 - 权重
     * @param costs 开销散列表
     * @param parents 父节点散列表 子节点 - 最小开销父节点
     */
    private static void dijkstra(Map<String, Map<String, Integer>> graph, Map<String, Integer> costs, Map<String, String> parents) {
        // 记录处理过的节点
        Set<String> processed = new HashSet<>();

        // 在未处理节点中找出开销最小的节点
        String node = findLowestCostNode(costs, processed);

        while(node != null) {
            int cost = costs.get(node);
            final Map<String, Integer> neighbors = graph.get(node);
            // 遍历子节点
            if (neighbors != null) {
                for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
                    int newCost = cost + entry.getValue();
                    // 经当前节点前往子节点更近
                    if (costs.get(entry.getKey()) > newCost) {
                        // 更新邻居开销
                        costs.put(entry.getKey(), newCost);
                        // 将子节点的父节点设置为当前节点
                        parents.put(entry.getKey(), node);
                    }
                }
            }
            processed.add(node);
            node = findLowestCostNode(costs, processed);
        }
    }

    private static Map<String, String> getParentMap() {
        Map<String, String> parents = new HashMap<>();
        parents.put(A, START);
        parents.put(B, START);
        return parents;
    }

    private static Map<String, Integer> getCostMap() {
        Map<String, Integer> costs = new HashMap<>();
        costs.put(A, 6);
        costs.put(B, 2);
        costs.put(END, Integer.MAX_VALUE);
        return costs;
    }

    private static Map<String, Map<String, Integer>> getGraphMap() {
        Map<String, Map<String, Integer>> graph = new HashMap<>();
        Map<String, Integer> startMap = new HashMap<>();
        startMap.put(A, 6);
        startMap.put(B, 2);
        graph.put(START, startMap);
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put(END, 1);
        graph.put(A, aMap);
        Map<String, Integer> bMap = new HashMap<>();
        bMap.put(A, 3);
        bMap.put(END, 5);
        graph.put(B, bMap);
        graph.put(END, null);
        return graph;
    }

    private static String findLowestCostNode(Map<String, Integer> costs, Set<String> processed) {
        int lowestCost = Integer.MAX_VALUE;
        String lowestCostNode = null;
        for (Map.Entry<String, Integer> entry : costs.entrySet()) {
            int cost = entry.getValue();
            if (cost < lowestCost && !processed.contains(entry.getKey())) {
                lowestCost = cost;
                lowestCostNode = entry.getKey();
            }
        }
        return lowestCostNode;
    }

}
