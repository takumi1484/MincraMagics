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

    /**
     * 値の桁数を取得します。
     * @param val 値
     * @return ex.12.345 -> 5
     */
    public static int getPrecision(Float val){
        // JavaではFloat⇒int変換するやりかたは無駄な処理が多いので文字列化して数える
        String str = String.valueOf(val);

        // 文末が ".0"とか".00000"で終わってるやつは全部桁０とする
//        if(str.matches("^.*\\.0+$")){
//            return 0;
//        }

        int index = str.indexOf(".");
        return str.substring(index + 1).length() + index;
    }
}
