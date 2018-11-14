package person.yx.solution;

import java.util.*;

/**
 * @author yuexing
 * created at 2018-11-07 10:34
 **/
public class Solution {
    public boolean isValid(String s) {
        Stack<Character> left = new Stack<>();
        char[] cs = s.toCharArray();
        for (char c : cs) {
            if (c == '(' || c == '[' || c == '{') {
                left.push(c);
            } else {
                if (left.empty()) {
                    return false;
                }
                switch (c) {
                    case ')':
                        if (left.pop() == '(') {
                            break;
                        } else {
                            return false;
                        }
                    case ']':
                        if (left.pop() == '[') {
                            break;
                        } else {
                            return false;
                        }
                    case '}':
                        if (left.pop() == '{') {
                            break;
                        } else {
                            return false;
                        }
                    default:
                        break;
                }
            }
        }
        return left.empty();
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return equals(root.left, root.right);
    }

    private boolean equals(TreeNode left, TreeNode right) {
        if (left == null || right == null) {
            return left == null && right == null;
        }
        return left.val == right.val && equals(left.left, right.right) && equals(left.right, right.left);
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        Stack<List<Integer>> stack = new Stack<>();
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        while (nodes.size() > 0) {
            List<Integer> temp = new ArrayList<>();
            for (TreeNode node : nodes) {
                temp.add(node.val);
            }
            stack.push(temp);
            nodes = getNextLevelNodes(nodes);
        }
        while (!stack.empty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private List<TreeNode> getNextLevelNodes(List<TreeNode> nodes) {
        List<TreeNode> nextLevelNodes = new ArrayList<>();
        nodes.forEach(n -> {
            if (n.left != null) nextLevelNodes.add(n.left);
            if (n.right != null) nextLevelNodes.add(n.right);
        });
        return nextLevelNodes;
    }

    public String convertToTitle(int n) {
        StringBuilder result = new StringBuilder();
        int num = n % 26;
        int shift = n / 26;
        result.append((char) (num + 64));
        while (shift > 0) {
            num = shift % 27;
            shift /= 27;
            result.append((char) (num + 64));
        }
        return result.reverse().toString();
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int lengthA = 0, lengthB = 0;
        ListNode tempA = headA, tempB = headB, result = null, index = null;
        while (tempA != null) {
            lengthA++;
            tempA = tempA.next;
        }
        while (tempB != null) {
            lengthB++;
            tempB = tempB.next;
        }
        // shift
        if (lengthA > lengthB) {
            for (int i = 0; i < lengthA - lengthB; i++) {
                headA = headA.next;
            }
        }
        if (lengthB > lengthA) {
            for (int i = 0; i < lengthB - lengthA; i++) {
                headB = headB.next;
            }
        }
        while (headA != null && headB != null) {
            if (headA.val == headB.val) {
                if (result == null) {
                    result = new ListNode(headA.val);
                    index = result;
                } else {
                    index.next = new ListNode(headA.val);
                    index = index.next;
                }
            }
            headA = headA.next;
            headB = headB.next;
        }
        return result;
    }

    /**
     * 167 两数之和 II - 输入有序数组
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        int right = numbers.length - 1, left = 0;
        while (right > left) {
            int small = numbers[left], big = numbers[right];
            if (small + big == target) {
                return new int[]{left + 1, right + 1};
            } else if (small + big > target) {
                right--;
            } else {
                left++;
            }
        }
        return new int[2];
    }

    /**
     * 169 majority-element
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        int threshold = nums.length / 2;
        for (int num : nums) {
            int now = count.getOrDefault(num, 0) + 1;
            if (now > threshold) {
                return num;
            } else {
                count.put(num, now);
            }
        }
        return 0;
    }

    /**
     * 344 reverse-strings
     *
     * @param s
     * @return
     */
    public String reverseString(String s) {
        if (s == null) {
            return null;
        }
        int len = s.length();
        char[] cs = s.toCharArray();
        char[] result = new char[len];
        for (int i = 0; i < len; i++) {
            result[len - 1 - i] = cs[i];
        }
        return new String(result);
    }

    /**
     * 206 reverse-linked-list
     *
     * @param head
     * @return
     */
    public ListNode reverseListLoop(ListNode head) {
        ListNode result = null;
        if (head != null) {
            result = new ListNode(head.val);
            while (head.next != null) {
                ListNode current = new ListNode(head.next.val);
                head = head.next;
                current.next = result;
                result = current;
            }
        }
        return result;
    }

    /**
     * 206 reverse-linked-list
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode current = new ListNode(head.val);
        if (head.next == null) {
            return current;
        }
        ListNode last = reverseList(head.next);
        last.next = current;
        current = last;
        return current;
    }

    /**
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        int len = n - m + 1;
        if (head == null || head.next == null || len <= 1) {
            return head;
        }
        int[] temp = new int[len];
        ListNode index = head;
        while (m > 1) {
            index = index.next;
            m--;
        }
        ListNode modify = index;
        for (int i = 0; i < len; i++) {
            temp[i] = index.val;
            index = index.next;
        }
        for (int i = 0; i < len; i++) {
            modify.val = temp[len - 1 -i];
            modify = modify.next;
        }
        return head;
    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}
