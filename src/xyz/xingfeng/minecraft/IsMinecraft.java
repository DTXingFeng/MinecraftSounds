package xyz.xingfeng.minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 扫描 .minecraft/assets/indexes，列出本机已下载资源索引的版本（即可导出的版本）
 */
public class IsMinecraft {

    private IsMinecraft() {
    }

    /**
     * @return 版本名列表，越新的版本越靠前；目录不存在或没有索引时返回空列表
     */
    public static List<String> scan(String mcRoot) {
        List<String> versions = new ArrayList<>();
        File[] files = new File(mcRoot, "assets/indexes").listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (file.isFile() && name.length() > ".json".length() && name.endsWith(".json")) {
                    versions.add(name.substring(0, name.length() - ".json".length()));
                }
            }
        }
        // 数字开头的版本（正式版 / 快照 / 编号索引）排在前面，组内越新越靠前
        VersionComparator natural = new VersionComparator();
        versions.sort((a, b) -> {
            boolean da = !a.isEmpty() && Character.isDigit(a.charAt(0));
            boolean db = !b.isEmpty() && Character.isDigit(b.charAt(0));
            if (da != db) {
                return da ? -1 : 1;
            }
            return natural.compare(b, a);
        });
        return versions;
    }

    /**
     * 版本号按自然顺序比较：数字段按数值比较（1.10 排在 1.9 之后），其余按字符比较
     */
    private static class VersionComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            int i = 0;
            int j = 0;
            while (i < a.length() && j < b.length()) {
                if (Character.isDigit(a.charAt(i)) && Character.isDigit(b.charAt(j))) {
                    int sa = i;
                    int sb = j;
                    while (i < a.length() && Character.isDigit(a.charAt(i))) i++;
                    while (j < b.length() && Character.isDigit(b.charAt(j))) j++;
                    // 去掉前导零后先比位数再比内容，避免长数字转 int 溢出
                    while (sa < i - 1 && a.charAt(sa) == '0') sa++;
                    while (sb < j - 1 && b.charAt(sb) == '0') sb++;
                    int la = i - sa;
                    int lb = j - sb;
                    if (la != lb) {
                        return la - lb;
                    }
                    int cmp = a.substring(sa, i).compareTo(b.substring(sb, j));
                    if (cmp != 0) {
                        return cmp;
                    }
                } else {
                    if (a.charAt(i) != b.charAt(j)) {
                        return a.charAt(i) - b.charAt(j);
                    }
                    i++;
                    j++;
                }
            }
            return (a.length() - i) - (b.length() - j);
        }
    }
}
