package zephyr.algorithms.linkedlist;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class FindNode {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        l1.next.next.next = new ListNode(4);
        l1.next.next.next.next = new ListNode(5);

        log.info("{}", findTailNode(l1, 1).val);
        log.info("{}", findTailNode(l1, 2).val);
        log.info("{}", findTailNode(l1, 3).val);
        log.info("{}", findTailNode(l1, 4).val);
        log.info("{}", findTailNode(l1, 5).val);

        ListNode currentNode = removeTailNode(l1, 2);
        while (currentNode != null) {
            log.info("{}", currentNode.val);
            currentNode = currentNode.next;
        }
    }

    // 输出倒数第k个节点，即正数第L-k+1个节点
    // 定义两个指向头结点，node1先跑k-1个，node2开始跑，当node1跑完时，node2就正好是倒数第k个
    public static ListNode findTailNode(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        ListNode node1 = head;
        ListNode node2 = head;
        int count = 0; // 记录遍历节点数
        int index = k; // 记录node1次数
        while (node1 != null) {
            node1 = node1.next;
            count++;
            if (index < 1) {
                node2 = node2.next;
            }
            index--;
        }
        // 节点个数小于k
        if (count < k) {
            return null;
        }
        return node2;
    }

    public static ListNode removeTailNode(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode node1 = dummy;
        ListNode node2 = dummy;

        while (node1 != null) {
            node1 = node1.next;
            if (k < 1 && node1 != null) {
                node2 = node2.next;
            }
            k--;
        }

        node2.next = node2.next.next;

        return dummy.next;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
