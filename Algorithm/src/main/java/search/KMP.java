package search;

/**
 * 约定:
 * 用 pat 表示模式串，长度为 M，txt 表示文本串，长度为 N。
 * KMP 算法是在 txt 中查找子串 pat，如果存在，返回这个子串的起始索引，否则返回 -1。
 */
public class KMP {
    private int[][] dp;
    private String pat;

    public static void main(String[] args) {
        String txt = "abcabcdabcdeabcedf";
        String pat = "abcde";
        System.out.println("@@@:" + way1_find(txt, pat));
        System.out.println("@@@:" + way2_Roger(txt, pat));
    }

    /**
     * Refer to : https://zhuanlan.zhihu.com/p/403452365
     *
     * @param txt 主串
     * @param pat 模式串
     */
    public static int way1_find(String txt, String pat) {
        //1.建立next数组
        int pLeft = 0, pRight = 1;//一开始两个相同的值比无意义，所以pRight直接置1
        int[] next = new int[pat.length()];

        while (pRight < pat.length()) {
            if (pat.charAt(pLeft) == pat.charAt(pRight)) {
                pLeft++;
                next[pRight] = pLeft;
                pRight++;
            } else if (pat.charAt(pLeft) != pat.charAt(pRight) && pLeft != 0) {
                pLeft = next[pLeft - 1];
            } else {
                next[pRight] = pLeft;
                pRight++;
            }
        }

        int p_pat = 0, p_txt = 0;
        while (p_txt < txt.length()) {
            if (pat.charAt(p_pat) == txt.charAt(p_txt)) {
                p_txt++;
                p_pat++;
            } else if (txt.charAt(p_txt) != pat.charAt(p_pat) && p_pat != 0) {
                p_pat = next[p_pat - 1];
            } else {
                p_txt++;
            }

            if (p_pat == pat.length()) {
                return p_txt - pat.length();
            }
        }
        return -1;
    }

    /**
     * Refer to Roger's best way
     * @param haystack
     * @param needle
     * @return
     */
    public static int way2_Roger(String haystack, String needle) {
        char[] ch = haystack.toCharArray();
        char[] nh = needle.toCharArray();
        if (nh.length == 0) {
            return 0;
        }
        int[] next = new int[nh.length + 1];
        int k = -1;
        int j = 0;
        next[0] = -1;
        while (j < nh.length) {
            if (k == -1 || nh[k] == nh[j]) {
                ++k;
                ++j;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
        int i = -1;
        j = -1;
        while (i < ch.length && j < nh.length) {
            if (j == -1 || ch[i] == nh[j]) {
                ++i;
                ++j;
            } else {
                j = next[j];
            }
        }
        if (j == nh.length) {
            return i - j;
        }
        return -1;
    }
}
