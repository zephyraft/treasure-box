package zephyr.algorithms.other;

import java.util.Stack;

/**
 * 栈的压入，弹出序列
 * 入栈 1,2,3,4,5
 * 出栈 4,5,3,2,1
 * 判断出栈序列是否为合法的弹出序列
 *
 * Created by zephyr on 2020/6/16.
 */
public class StackSequence {

    public static void main(String[] args) {
        System.out.println(isLegalPop(new int[]{1,2,3,4,5}, new int[]{4,5,3,2,1}));
        System.out.println(isLegalPop(new int[]{1,2,3,4,5}, new int[]{4,3,5,1,2}));
    }

    public static boolean isLegalPop(int[] push, int[] pop) {
        if (pop.length == 0 || push.length != pop.length) {
            return false;
        }
        // 辅助栈
        Stack<Integer> stack = new Stack<>();
        // 标识弹出序列的位置
        int popIndex = 0;
        for (int value : push) {
            stack.push(value);
            while (!stack.empty() && stack.peek() == pop[popIndex]) {
                stack.pop();
                popIndex++;
            }
        }
        return stack.empty();
    }


}
