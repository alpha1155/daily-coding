// 2071. 你可以安排的最多任务数目
// 困难
// 相关标签
// 相关企业
// 提示
// 给你 n 个任务和 m 个工人。每个任务需要一定的力量值才能完成，需要的力量值保存在下标从 0 开始的整数数组 tasks 中，第 i 个任务需要 tasks[i]
// 的力量才能完成。每个工人的力量值保存在下标从 0 开始的整数数组 workers 中，第 j 个工人的力量值为 workers[j] 。每个工人只能完成 一个 任务，且力量值需要 大于等于
// 该任务的力量要求值（即 workers[j] >= tasks[i] ）。

// 除此以外，你还有 pills 个神奇药丸，可以给 一个工人的力量值 增加 strength 。你可以决定给哪些工人使用药丸，但每个工人 最多 只能使用 一片 药丸。

// 给你下标从 0 开始的整数数组tasks 和 workers 以及两个整数 pills 和 strength ，请你返回 最多 有多少个任务可以被完成。

// 示例 1：

// 输入：tasks = [3,2,1], workers = [0,3,3], pills = 1, strength = 1
// 输出：3
// 解释：
// 我们可以按照如下方案安排药丸：
// - 给 0 号工人药丸。
// - 0 号工人完成任务 2（0 + 1 >= 1）
// - 1 号工人完成任务 1（3 >= 2）
// - 2 号工人完成任务 0（3 >= 3）
// 示例 2：

// 输入：tasks = [5,4], workers = [0,0,0], pills = 1, strength = 5
// 输出：1
// 解释：
// 我们可以按照如下方案安排药丸：
// - 给 0 号工人药丸。
// - 0 号工人完成任务 0（0 + 5 >= 5）
// 示例 3：

// 输入：tasks = [10,15,30], workers = [0,10,10,10,10], pills = 3, strength = 10
// 输出：2
// 解释：
// 我们可以按照如下方案安排药丸：
// - 给 0 号和 1 号工人药丸。
// - 0 号工人完成任务 0（0 + 10 >= 10）
// - 1 号工人完成任务 1（10 + 10 >= 15）
// 示例 4：

// 输入：tasks = [5,9,8,5,9], workers = [1,6,4,2,6], pills = 1, strength = 5
// 输出：3
// 解释：
// 我们可以按照如下方案安排药丸：
// - 给 2 号工人药丸。
// - 1 号工人完成任务 0（6 >= 5）
// - 2 号工人完成任务 2（4 + 5 >= 8）
// - 4 号工人完成任务 3（6 >= 5）

// 提示：

// n == tasks.length
// m == workers.length
// 1 <= n, m <= 5 * 104
// 0 <= pills <= m
// 0 <= tasks[i], workers[j], strength <= 109

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        int[] result = main.findEvenNumbers(new int[] {2, 1, 3, 0});
        System.out.println(Arrays.toString(result));
    }

    public long minSum(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        long nums1Sum = 0, nums2Sum = 0;
        int count1 = 0, count2 = 0;
        for (int i = 0; i < len1; i++) {
            nums1Sum += nums1[i];
            if (nums1[i] == 0) {
                count1++;
                nums1Sum++;
            }
        }
        for (int i = 0; i < len2; i++) {
            nums2Sum += nums2[i];
            if (nums2[i] == 0) {
                count2++;
                nums2Sum++;
            }
        }
        if ((count1 == 0 && nums1Sum < nums2Sum) || (count2 == 0 && nums2Sum < nums1Sum)) {
            return -1;
        }
        return Math.max(nums1Sum, nums2Sum);

    }

    // 2071. 你可以安排的最多任务数目
    public int[] buildArray(int[] nums) {
        int len = nums.length;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = nums[nums[i]];
        }
        return ans;
    }

    public boolean threeConsecutiveOdds(int[] arr) {
        for (int i = 0; i < arr.length - 2; i++) {
            if (arr[i] % 2 == 1 && arr[i + 1] % 2 == 1 && arr[i + 2] % 2 == 1) {
                return true;
            }
        }
        return false;
    }

    public int numEquivDominoPairs(int[][] dominoes) {
        int ans = 0;
        int[] nums = new int[100];
        for (int i = 0; i < dominoes.length; i++) {
            int a = dominoes[i][0];
            int b = dominoes[i][1];
            if (a < b) {
                int temp = a;
                a = b;
                b = temp;
            }
            ans += nums[a * 10 + b]++;
        }
        return ans;
    }

    public int[] findEvenNumbers(int[] digits) {
        int[] count = new int[1000]; // 统计每个三位数的出现次数
        int len = digits.length;
        
        // 生成所有可能的三位数排列
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (i == j) continue; // 不能重复使用同一位置的数字
                for (int k = 0; k < len; k++) {
                    if (i == k || j == k) continue; // 不能重复使用同一位置的数字
                    
                    int hundreds = digits[i];
                    int tens = digits[j];
                    int ones = digits[k];
                    
                    // 确保百位不为0，且个位为偶数
                    if (hundreds != 0 && ones % 2 == 0) {
                        int num = hundreds * 100 + tens * 10 + ones;
                        count[num]++; // 统计合法三位数的频率
                    }
                }
            }
        }
        
        // 收集结果并排序
        List<Integer> result = new ArrayList<>();
        for (int i = 100; i < 1000; i += 2) { // 直接遍历偶数
            if (count[i] > 0) {
                result.add(i);
            }
        }
        
        // 转换为int数组并返回
        return result.stream(). ().mapToInt(Integer::intValue).toArray();
    }

}
