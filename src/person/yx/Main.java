package person.yx;

import person.yx.solution.Solution;

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.thirdMax(new int[]{1,2,-2147483648}));
    }
}
