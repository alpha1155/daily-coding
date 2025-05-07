// 1128. 等价多米诺骨牌对的数量
// 简单
// 相关标签
// 相关企业
// 提示
// 给你一组多米诺骨牌 dominoes 。

// 形式上，dominoes[i] = [a, b] 与 dominoes[j] = [c, d] 等价 当且仅当 (a == c 且 b == d) 或者 (a == d 且 b == c) 。即一张骨牌可以通过旋转 0 度或 180 度得到另一张多米诺骨牌。

// 在 0 <= i < j < dominoes.length 的前提下，找出满足 dominoes[i] 和 dominoes[j] 等价的骨牌对 (i, j) 的数量。

// 示例 1：

// 输入：dominoes = [[1,2],[2,1],[3,4],[5,6]]
// 输出：1
// 示例 2：

// 输入：dominoes = [[1,2],[1,2],[1,1],[1,2],[2,2]]
// 输出：3

// 提示：

// 1 <= dominoes.length <= 4 * 104
// dominoes[i].length == 2
// 1 <= dominoes[i][j] <= 9
/**
 * @param {number[][]} dominoes
 * @return {number}
 */
var numEquivDominoPairs = function (dominoes) {
  let nums = new Array(100).fill(0)
  let ans = 0
  for (let i = 0; i < dominoes.length; i++) {
    let count = dominoes[i][0] > dominoes[i][1] ? dominoes[i][0] * 10 + dominoes[i][1] : dominoes[i][1] * 10 + dominoes[i][0]
    ans += nums[count]
    nums[count]++
  }
  return ans
}
