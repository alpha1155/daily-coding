class Solution {
    public int minOperations(int[] nums) {
        int len = nums.length;
        int num1 = 0;
        int g = 0;
        for (int x : nums) {
            if (x == 1)
                num1++;
            g = gcd(g, x);
        }
        if (num1 > 0) {
            return len - num1;
        }
        if (g != 1) {
            return -1;
        }
        int minLen = len;
        for (int i = 0; i < len; i++) {
            int currGcd = 0;
            for (int j = i; j < len; j++) {
                currGcd = gcd(currGcd, nums[j]);
                if (currGcd == 1) {
                    minLen = Math.min(minLen, j - i + 1);
                    break;
                }
            }
        }
        return minLen + len - 2;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
