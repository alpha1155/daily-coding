class Solution {
    public int numberOfSubstrings(String s) {
        int len = s.length();
        int result = 0;
        int[] prefix = new int[len + 1];
        prefix[0] = -1;
        for (int i = 0; i < len; i++) {
            if (i == 0 || s.charAt(i - 1) == '0')
                prefix[i + 1] = i;
            else
                prefix[i + 1] = prefix[i];
        }
        System.out.println(prefix);
        for (int i = 1; i <= len; i++) {
            int cnt0 = s.charAt(i - 1) == '0' ? 1 : 0;
            int j = i;
            while (j > 0 && cnt0 * cnt0 <= len) {
                int cnt1 = (i - prefix[j]) - cnt0;
                if (cnt0 * cnt0 <= cnt1) {
                    result += Math.min(j - prefix[j], cnt1 - cnt0 * cnt0 + 1);
                }
                System.out.println(cnt0, cnt1, j, prefix[j]);
                j = prefix[j];
                cnt0++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        String s = "001101";
        int res = sol.maxSumDivThree([2,8,32,38,13,40]);
        System.out.println(res); // Expected output: 12
    }
    
    public int maxSumDivThree(int[] nums) {
        int minOne = 10000, secOne = 10000, minTwo = 10000, secTwo = 10000, len = nums.length, result = 0;
        for (int n : nums) {
            result += n;
            if (n % 3 == 1) {
                if (minOne >= n) {
                    secOne = minOne;
                    minOne = n;
                }
                // minOne = Math.min(minOne, n);
            } else if (n % 3 == 2) {
                if (minTwo >= n) {
                    secTwo = minTwo;
                    minTwo = n;
                }
                // minTwo = Math.min(minTwo, n);
            }
        }
        if (result % 3 == 2)
            result -= Math.min(minOne + secOne, minTwo);
        else if (result % 3 == 1)
            result -= Math.min(minTwo + secTwo, minOne);
        return result;
    }
}