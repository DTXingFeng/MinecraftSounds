package xyz.xingfeng.minecraft;

import java.io.*;

/**
 * @author xing_Feng
 */
public class FileDo {
    File f;

    /**
     * FileDo的构造体
     * 
     * @param file
     */
    public FileDo(File file) {
        this.f = file;
    }

    /**
     * 这里进行封装操作
     * 
     * @param
     * @return
     * @throws IOException
     */
    public String copy() throws IOException {
        String s = "";
        FileReader r = new FileReader(f);
        int ch = 0;
        while ((ch = r.read()) != -1) {
            s += (char) ch;
        }
        return s;
    }

    /**
     * 需要追加的内容，
     * zuijia("123")
     * 
     * @return
     */
    public void zuijia(String s) throws IOException {
        String st = copy();
        FileWriter w = new FileWriter(f);
        st += s;
        w.write(st);
        w.close();
    }

    /**
     * 用于对比的封装,
     * 查看目标文件内有无一模一样的字符,
     * 有则返回行数,
     * 否则返回0
     */
    public int duibi(String s) throws IOException {
        int num = 0;
        FileReader r = new FileReader(f);
        BufferedReader b = new BufferedReader(r);
        String st = null;
        while ((st = b.readLine()) != null) {
            num++;
            if (st.equals(s)) {
                System.out.println("文件内有一致的字符，在第" + num + "行");
                r.close();
                b.close();
                return num;
            }
        }
        System.out.println("文件内无一致的字符");
        r.close();
        b.close();
        return 0;
    }

    /**
     * 修改某一行的字符,
     * xiugai(行，修改结果);
     */
    public void xiugai(int i, String s) throws IOException {
        FileReader r = new FileReader(f);
        BufferedReader b = new BufferedReader(r);
        String st = "";
        String str = null;
        int num = 1;
        while ((str = b.readLine()) != null) {
            if (num != i) {
                st += str + "\n";
            } else if (num == i) {
                st += s + "\n";
            }
            num++;
        }
        FileWriter w = new FileWriter(f);
        w.write(st);
        w.close();
        r.close();
        b.close();
    }

    /**
     * 提取某行内容
     * tiqu(行数);
     */
    public String tiqu(int i) {
        String s = null;
        FileReader r = null;
        try {
            r = new FileReader(f);
            BufferedReader b = new BufferedReader(r);
            int num = 1;
            while ((s = b.readLine()) != null) {
                if (num == i) {
                    return s;
                }
                num++;
            }
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 查看txt文件中有多少行
     * 返回值：int；
     * hangshu();
     */
    public int hangshu() throws IOException {
        String s = null;
        FileReader r = new FileReader(f);
        BufferedReader b = new BufferedReader(r);
        int num = 0;
        while ((s = b.readLine()) != null) {
            num++;
        }
        return num;
    }

    /**
     * 生成文件夹;
     * shengcheng();
     */
    public void shengcheng() throws IOException {
        f.mkdir();
    }
}
