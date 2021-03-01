package zephyr.algorithms.other;

import java.util.Stack;

/**
 * 两个栈实现 队列(先进先出)
 * Created by zephyr on 2020/6/16.
 */
public class StackList {

    private static final Stack<Integer> stack1 = new Stack<>();
    private static final Stack<Integer> stack2 = new Stack<>();

    public static void push(int value) {
        stack1.push(value);
    }

    public static int pop() {
        if (stack1.empty() && stack2.empty()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // 栈2为空，则把栈1压入栈2，相当于翻转栈1
        if (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public static void main(String[] args) {
        push(1);
        System.out.println(pop());
        push(2);
        push(3);
        push(4);
        System.out.println(pop());
        System.out.println(pop());
        push(5);
        push(6);
        System.out.println(pop());
        System.out.println(pop());
        System.out.println(pop());
    }
}
