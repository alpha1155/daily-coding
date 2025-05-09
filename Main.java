// 2071. 你可以安排的最多任务数目
// 困难
// 相关标签
// 相关企业
// 提示
// 给你 n 个任务和 m 个工人。每个任务需要一定的力量值才能完成，需要的力量值保存在下标从 0 开始的整数数组 tasks 中，第 i 个任务需要 tasks[i] 的力量才能完成。每个工人的力量值保存在下标从 0 开始的整数数组 workers 中，第 j 个工人的力量值为 workers[j] 。每个工人只能完成 一个 任务，且力量值需要 大于等于 该任务的力量要求值（即 workers[j] >= tasks[i] ）。

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

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        int[] result = main.buildArray(new int[] { 5, 0, 1, 2, 3, 4 });
        System.out.println(Arrays.toString(result));
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
}
