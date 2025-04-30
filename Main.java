public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Main, World! " + main.findNumbers(new int[] { 2, 1, 4, 3, 5 }));
    }

    public int findNumbers(int[] nums) {
        int count = 0;
        for (int num : nums) {
            if (String.valueOf(num).length() % 2 == 0) {
                count++;
            }
        }
        return count;
    }
}
