package leetcode;

import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=2154 lang=java
 * 
 * @lcpr version=30204
 *
 * [2154] 将找到的值乘以 2
 */


// @lcpr-template-start

// @lcpr-template-end
// @lc code=start
class Solution {
    public int findFinalValue(int[] nums, int original) {
        java.util.concurrent.atomic.AtomicInteger result = new java.util.concurrent.atomic.AtomicInteger(original);
        while (Arrays.stream(nums).anyMatch(n -> n == result.get())) {
            result.set(result.get() * 2);
        }
        return result.get();
    }
}
// @lc code=end



/*
 * // @lcpr case=start // [5,3,6,1,12]\n3\n // @lcpr case=end
 * 
 * // @lcpr case=start // [2,7,9]\n4\n // @lcpr case=end
 * 
 */

