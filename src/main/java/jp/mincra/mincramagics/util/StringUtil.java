package jp.mincra.mincramagics.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    /**
     * 文字列に含まれる文字を返す。
     * @param str
     * @return
     */
    public static List<Character> getContainsCharacter(String str) {

        char[] c = str.toCharArray();

        List<Character> finalList = new ArrayList<>();

        for (int i=0, len=c.length; i<len; i++){
            char tempChar = c[i];
            if (!finalList.contains(tempChar) && tempChar != ' ') {
                finalList.add(c[i]);
            }
        }

        return finalList;
    }
}
