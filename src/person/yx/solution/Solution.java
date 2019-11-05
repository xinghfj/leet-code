package person.yx.solution;

import java.util.*;
import java.util.stream.IntStream;

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
            modify.val = temp[len - 1 - i];
            modify = modify.next;
        }
        return head;
    }

    /**
     * 217
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            } else {
                set.add(num);
            }
        }
        return false;
    }

    /**
     * 219
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (k == 0) {
            return false;
        }
        for (int i = 0; i < nums.length - k; i++) {
            if (nums[i] == nums[i + k]) {
                return true;
            }
        }
        return false;
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null || (root.right == null && root.left == null)) {
            return root;
        }
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return root;
    }

    public boolean isPalindrome(ListNode head) {
        List<Integer> content = new ArrayList<>();
        while (head != null) {
            content.add(head.val);
            head = head.next;
        }
        if (content.size() > 0) {
            int len = content.size();
            for (int i = 0; i < (len + 1) / 2; i++) {
                if (!content.get(i).equals(content.get(len - i - 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        return null;
    }

    public void deleteNode(ListNode node) {
        System.out.println();
    }

    /**
     * 242
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s == null) {
            return t == null;
        } else if (t == null || s.length() != t.length()) {
            return false;
        }
        char[] ss = s.toCharArray();
        char[] ts = t.toCharArray();
        Arrays.sort(ss);
        Arrays.sort(ts);
        for (int i = 0; i < ss.length; i++) {
            if (ss[i] != ts[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 257
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new LinkedList<>();
        if (root != null) {
            String path = String.valueOf(root.val);
            if (root.left == null && root.right == null) {
                result.add(path);
            } else {
                if (root.left != null) {
                    getPath(root.left, result, new StringBuilder(path));
                }
                if (root.right != null) {
                    getPath(root.right, result, new StringBuilder(path));
                }
            }
        }
        return result;
    }

    private void getPath(TreeNode node, List<String> result, StringBuilder path) {
        if (node.left == null && node.right == null) {
            result.add(path.toString() + "->" + node.val);
        }
        if (node.left != null) {
            getPath(node.left, result, new StringBuilder(path).append("->").append(node.val));
        }
        if (node.right != null) {
            getPath(node.right, result, new StringBuilder(path).append("->").append(node.val));
        }
    }

    public int addDigits(int num) {
        if (num < 10) {
            return num;
        }
        char[] cs = String.valueOf(num).toCharArray();
        int temp = 0;
        for (char c : cs) {
            temp += (c - '0');
        }
        return addDigits(temp);
    }

    public boolean isUgly(int num) {
        if (num < 1) {
            return false;
        }
        while (num > 1) {
            if (num % 5 == 0) {
                num /= 5;
            } else if (num % 3 == 0) {
                num /= 3;
            } else if (num % 2 == 0) {
                num /= 2;
            } else {
                return false;
            }
        }
        return true;
    }

    public int missingNumber(int[] nums) {
        int[] temp = new int[nums.length + 1];
        for (int i : nums) {
            temp[i] = i;
        }
        for (int i = 1; i < temp.length; i++) {
            if (temp[i] == 0) {
                return i;
            }
        }
        return 0;
    }

    public int firstBadVersion(int n) {
        if (n == 1 && isBadVersion(1)) {
            return 1;
        }
        int start = 1, end = n;
        while (end > start) {
            System.out.println("end: " + end + ", start: " + start);
            int mid = (end / 2 + start / 2);
            if (end % 2 == 1 && start % 2 == 1) {
                mid++;
            }
            if (isBadVersion(mid)) {
                if (!isBadVersion(mid - 1)) {
                    return mid;
                } else {
                    end = mid;
                }
            } else {
                if (isBadVersion(mid + 1)) {
                    return mid + 1;
                } else {
                    start = mid;
                }
            }
        }
        return 0;
    }

    private boolean isBadVersion(int version) {
        return version >= 1702766719;
    }

    public void moveZeroes(int[] nums) {

    }

    /**
     * 290
     *
     * @param pattern
     * @param str
     * @return
     */
    public boolean wordPattern(String pattern, String str) {
        if (Objects.isNull(pattern) || Objects.isNull(str)) {
            return false;
        }
        String[] splitStr = str.split(" ");
        char[] splitPattern = pattern.toCharArray();
        HashMap<Character, String> result = new HashMap<>();
        if (splitPattern.length != splitStr.length) {
            return false;
        }
        for (int i = 0; i < splitPattern.length; i++) {
            char c = splitPattern[i];
            String s = splitStr[i];
            if (!result.containsKey(c)) {
                result.put(c, s);
            } else if (!s.equals(result.get(c))) {
                return false;
            }
        }
        Set<String> vals = new HashSet<>(result.values());
        return vals.size() == result.size();
    }

    public boolean isPowerOfThree(int n) {

        return true;
    }

    public boolean isPowerOfFour(int num) {
        if (num == 0) {
            return true;
        }
        return (num & (num - 1)) == 0 && Integer.toBinaryString(num).length() % 2 == 1;
    }

    /**
     * 345
     *
     * @param s
     * @return
     */
    public String reverseVowels(String s) {
        if (s == null) {
            return null;
        }
        char[] cs = s.toCharArray();
        Map<Integer, Character> map = new TreeMap<>();
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (c == 'a' || c == 'e' || c == 'o' || c == 'u' || c == 'i' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                map.put(i, c);
            }
        }
        List<Integer> keys = new ArrayList<>(map.keySet());
        int len = keys.size();
        for (int i = 0; i < (len + 1) / 2; i++) {
            cs[keys.get(i)] = map.get(keys.get(len - 1 - i));
            cs[keys.get(len - 1 - i)] = map.get(keys.get(i));
        }
        return new String(cs);
    }

    /**
     * 349
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection(int[] nums1, int[] nums2) {
        List<Integer> list = new ArrayList<>();
        for (int i1 : nums1) {
            if (!list.contains(i1)) {
                for (int i : nums2) {
                    if (i1 == i) {
                        list.add(i1);
                        break;
                    }
                }
            }
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0) {
            return new int[0];
        }
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> list = new ArrayList<>();
        int index1 = 0;
        int index2 = 0;
        while (index1 < nums1.length && index2 < nums2.length) {
            if (nums1[index1] == nums2[index2]) {
                list.add(nums1[index1]);
                index1++;
                index2++;
            } else if (nums1[index1] < nums2[index2]) {
                index1++;
            } else {
                index2++;
            }
        }
        int[] result = new int[list.size()];
        int start = 0;
        for (int i : list) {
            result[start++] = i;
        }
        return result;
    }

    /**
     * 367
     *
     * @param num
     * @return
     */
    public boolean isPerfectSquare(int num) {
        if (num <= 0) {
            return false;
        }
        if (num == 1) {
            return true;
        }
        int start = 2;
        int end = num / 2;
        while (start <= end) {
            if (start * start == num) {
                return true;
            } else if (start * start < num) {
                start++;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 371
     *
     * @param a
     * @param b
     * @return
     */
    public int getSum(int a, int b) {
        if (b == 0) {
            return a;
        }
        return getSum((a ^ b), (a & b) << 1);
    }

    /**
     * 392
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        int index = -1;
        for (char c : s.toCharArray()) {
            index = t.indexOf(c, index);
            if (index == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 389
     *
     * @param s
     * @param t
     * @return
     */
    public char findTheDifference(String s, String t) {
        char[] ts = t.toCharArray();
        char[] ss = s.toCharArray();
        Arrays.sort(ts);
        Arrays.sort(ss);
        for (int i = 0; i < ts.length; i++) {
            if (i == ts.length - 1 || ss[i] != ts[i]) {
                return ts[i];
            }
        }
        return ts[ts.length - 1];
    }

    /**
     * 400
     *
     * @param n
     * @return
     */
    public int findNthDigit(int n) {
        return 0;
    }

    /**
     * 374
     *
     * @param n
     * @return
     */
    public int guessNumber(int n) {
        long start = 1, end = n;
        while (start <= end) {
            int middle = (int) ((start + end) / 2);
            int result = guess(middle);
            if (result == 0) {
                return middle;
            } else if (result == -1) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
            System.out.println("start: " + start + ",end: " + end);
        }
        return 1;
    }

    int guess(int num) {
        return Integer.compare(1702766719, num);
    }

    public boolean canConstruct(String ransomNote, String magazine) {
        char[] cs = magazine.toCharArray();
        char[] rs = ransomNote.toCharArray();
        for (char c : rs) {
            int index = magazine.indexOf(c);
            if (index == -1) {
                return false;
            }
            cs[index] = '0';
            magazine = new String(cs);
        }
        return true;
    }

    public int firstUniqChar(String s) {
        char[] cs = s.toCharArray();
        int len = cs.length;
        List<Character> exist = new ArrayList<>();
        out:
        for (int i = 0; i < len; i++) {
            char c = cs[i];
            if (exist.contains(c)) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (c == cs[j]) {
                    exist.add(c);
                    continue out;
                }
            }
            return i;
        }
        return -1;
    }

    /**
     * 401
     *
     * @param num
     * @return
     */
    public List<String> readBinaryWatch(int num) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 60; j++) {
                if (count1(i) + count1(j) == num) {
                    result.add(i + ":" + (j > 9 ? j : "0" + j));
                }
            }
        }
        return result;
    }

    private int count1(int num) {
        int result = 0;
        while (num != 0) {
            num = num & (num - 1);
            result++;
        }
        return result;
    }

    /**
     * 404
     *
     * @param root
     * @return
     */
    public int sumOfLeftLeaves(TreeNode root) {
        return leftLevels(root, true);
    }

    private int leftLevels(TreeNode root, boolean left) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            if (left) {
                return root.val;
            } else {
                return 0;
            }
        }
        return leftLevels(root.left, true) + leftLevels(root.right, false);
    }

    /**
     * 405
     *
     * @param num
     * @return
     */
    public String toHex(int num) {
        if (num == 0)
            return "0";
        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 8 && num != 0) {
            sb.append(chars[num & 0xf]);
            num >>= 4;
        }
        return sb.reverse().toString();
    }

    /**
     * 409
     *
     * @param s
     * @return
     */
    public int longestPalindrome(String s) {
        int length = 0;
        if (s != null && s.length() > 0) {
            char[] cs = s.toCharArray();
            Arrays.sort(cs);
            for (int i = 0; i < cs.length - 1; ) {
                if (cs[i] == cs[i + 1]) {
                    i += 2;
                    length += 2;
                } else {
                    i++;
                }
            }
            if (length < s.length()) {
                length++;
            }
        }
        return length;
    }

    /*
    412
     */
    public List<String> fizzBuzz(int n) {
        List<String> out = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0) {
                if (i % 5 == 0) {
                    out.add("FizzBuzz");
                } else {
                    out.add("Fizz");
                }
            } else if (i % 5 == 0) {
                out.add("Buzz");
            } else {
                out.add(i + "");
            }
        }
        return out;
    }

    /*
    414
     */
    public int thirdMax(int[] nums) {
        Integer first = null;
        Integer second = null;
        Integer third = null;
        int differentNum = 0;
        for (Integer i : nums) {
            if (i.equals(third) || i.equals(second) || i.equals(first)) {
                continue;
            }
            differentNum++;
            if (third == null || i > third) {
                third = i;
            }
            if (second == null || third > second) {
                Integer temp = second;
                second = third;
                third = temp;
            }
            if (first == null || second > first) {
                Integer temp = first;
                first = second;
                second = temp;
            }
        }
        if (differentNum < 3) {
            return first == null ? 0 : first;
        }
        return third == null ? 0 : first;
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

class MyStack {
    private List<Integer> data = new ArrayList<>();
    private int len = 0;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {

    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        data.add(x);
        len++;
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        int val = data.get(len - 1);
        data.remove(len - 1);
        len--;
        return val;
    }

    /**
     * Get the top element.
     */
    public int top() {
        int val = data.get(len - 1);
        return val;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return data.isEmpty();
    }
}

class MyQueue {
    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    /**
     * Initialize your data structure here.
     */
    public MyQueue() {

    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        stack1.push(x);
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        if (stack2.empty()) {
            int len = stack1.size();
            IntStream.range(0, len).forEach(i -> stack2.push(stack1.pop()));
        }
        return stack2.pop();
    }

    /**
     * Get the front element.
     */
    public int peek() {
        if (stack2.empty()) {
            int len = stack1.size();
            IntStream.range(0, len).forEach(i -> stack2.push(stack1.pop()));
        }
        return stack2.peek();
    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        return stack1.empty() && stack2.empty();
    }
}

class NumArray {
    private int[] arr;

    public NumArray(int[] nums) {
        arr = new int[nums.length + 1];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            arr[i + 1] = sum;
        }
    }

    public int sumRange(int i, int j) {
        return arr[j + 1] - arr[i];
    }
}