package zephyr.algorithms.linkedlist;

import lombok.extern.slf4j.Slf4j;

/**
 * 联表两数相加
 * 输⼊：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * <p>
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class AddTwoNumbers {

    // [2,4,3]
    // [5,6,4]
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode currentNode = addTwoNumbers(l1, l2);
        while (currentNode != null) {
            log.info("{}", currentNode.val);
            currentNode = currentNode.next;
        }
    }

    // 对头节点进行操作时，考虑创建假节点dummy
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(0);
        ListNode currentNode = dummyNode;
        int carry = 0; // 进位
        while (l1 != null || l2 != null) {
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;
            int sum = carry + x + y;
            carry = sum / 10; // 计算进位
            currentNode.next = new ListNode(sum % 10);
            currentNode = currentNode.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        // 最终若还需要进位
        if (carry > 0) {
            currentNode.next = new ListNode(carry);
        }
        return dummyNode.next;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
