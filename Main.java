public class Main {
    public String triangleType(int[] var1) {
        if (var1[1] + var1[2] > var1[0] && var1[0] + var1[1] > var1[2]
                && var1[0] + var1[2] > var1[1]) {
            if (var1[0] == var1[1] && var1[1] == var1[2]) {
                return "equilateral";
            } else {
                return var1[0] != var1[1] && var1[0] != var1[2] && var1[2] != var1[1] ? "scalene"
                        : "isosceles";
            }
        } else {
            return "none";
        }
    }

    public static void main(String[] var0) {
        Main var1 = new Main();
        String var2 = var1.triangleType(new int[] {3, 4, 5});
        System.out.println(var2);
    }
}
