package jp.mincra.mincramagics.util;

public class MathUtil {
    public static int getGCD (int a, int b) {
        int temp;
        while((temp = a%b)!=0) {
            a = b;
            b = temp;
        }
        return b;
    }
}
