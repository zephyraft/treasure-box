package zephyr.algorithms.linkedlist;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class MergeNode {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(3);
        l1.next.next = new ListNode(6);

        ListNode l2 = new ListNode(4);
        l2.next = new ListNode(5);
        l2.next.next = new ListNode(6);

        ListNode merge = merge(l1, l2);
        while (merge != null) {
            log.info("{}", merge.val);
            merge = merge.next;
        }
    }

    // l1和l2单调递增
    public static ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val <= l2.val) {
            // l1 小于 l2，则l1 为前驱节点，然后继续递归比较l1的后继节点与l2
            l1.next = merge(l1.next, l2);
            return l1;
        } else {
            // l2 小于 l1，则l2 为前驱节点，然后继续递归比较l2的后继节点与l1
            l2.next = merge(l1, l2.next);
            return l2;
        }
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
