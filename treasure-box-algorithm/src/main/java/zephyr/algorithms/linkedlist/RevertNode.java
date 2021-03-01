package zephyr.algorithms.linkedlist;

import lombok.extern.slf4j.Slf4j;

/**
 * 翻转链表
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class RevertNode {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        l1.next.next.next = new ListNode(4);
        l1.next.next.next.next = new ListNode(5);
        ListNode currentNode = ReverseList(l1);
        while (currentNode != null) {
            log.info("{}", currentNode.val);
            currentNode = currentNode.next;
        }
    }

    // 对头节点进行操作时，考虑创建假节点dummy
    public static ListNode ReverseList(ListNode listNode) {
        ListNode pre = null;
        ListNode next;
        ListNode current = listNode;
        while (current != null) {
            // 下一个节点
            next = current.next;
            // 当前节点翻转
            current.next = pre;
            // 上一个节点
            pre = current;
            // 向链尾遍历
            current = next;
        }
        return pre;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
