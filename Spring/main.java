// Removed package declaration to match the expected package

import java.util.ArrayList;
import java.util.List;

public class main {
    public void main(String[] args) throws Exception {
        minOperations(new int[] {2, 6, 3, 4});
    }

    public int minOperations(int[] nums) {
        List<Integer> list = new ArrayList<>();
        /*
         * 寻找1
         * for length长度，每次找公约数
        */
        for (int i = 0; i < nums.length - 1; i++) {
            Integer gcd = gcd(nums[i], nums[i + 1]);
            list.add(gcd);
            if (gcd == 1) {
                System.out.println(list);
                return 0;
            }
        }
        return -1;
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
