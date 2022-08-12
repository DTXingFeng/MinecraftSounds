package xyz.xingfeng.minecraft;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Sounds implements Runnable{
    String path;
    String soundsPath;
    String exportPath;
    public Sounds(String path){
        this.path = Window.text.getText() + "\\assets\\indexes\\" + path + ".json";
        this.soundsPath = Window.text.getText() + "\\assets\\objects\\";
        this.exportPath = Window.text1.getText();
    }
    @Override
    public void run(){

        ArrayList<String> oggPath = new ArrayList<>();
        ArrayList<String> hash = new ArrayList<>();
        FileDo fileDo = new FileDo(new File(path));
        try {
            String json = fileDo.copy();
            while (true){
                if (json.contains("minecraft/sounds")){
                    json = json.substring(json.indexOf("minecraft/sounds"));
                    //获取提取后的路径
                    oggPath.add(json.substring(json.indexOf("minecraft/sounds"),json.indexOf("\"")));
                    //获取哈希值
                    int num = json.indexOf(",");
                    hash.add(json.substring(json.indexOf("hash") + 8, json.indexOf(",") - 1));
                    json = json.substring(num);
                }else {
                    break;
                }
            }
            //开始复制并重命名
            for (int i = 0; i < oggPath.size(); i++) {
                Window.jl4.setText(i + "/" + oggPath.size());
                //获取加密文件位置
                String MD5ogg = soundsPath + "\\" + hash.get(i).substring(0,2) + "\\" + hash.get(i);
                //文件解密后的位置以及命名
                String noMD5ogg = exportPath + "\\" + oggPath.get(i);
                File file = new File(MD5ogg);
                System.out.println(MD5ogg + "\t" + noMD5ogg);
                if (file.exists()){
                    File file1 = new File(noMD5ogg.substring(0,noMD5ogg.lastIndexOf("/")));
                    if (file1.exists()){
                        FileInputStream in = new FileInputStream(new File(MD5ogg));
                        FileOutputStream out = new FileOutputStream(new File(noMD5ogg));
                        byte[] buff = new byte[10240]; //限制大小
                        int n = 0;
                        while ((n = in.read(buff)) != -1) {
                            out.write(buff, 0, n);
                        }
                        out.flush();
                        in.close();
                        out.close();
                    }else {
                        //没有创建文件就生成再复制
                        file1.mkdirs();
                        FileInputStream in = new FileInputStream(new File(MD5ogg));
                        FileOutputStream out = new FileOutputStream(new File(noMD5ogg));
                        byte[] buff = new byte[10240]; //限制大小
                        int n = 0;
                        while ((n = in.read(buff)) != -1) {
                            out.write(buff, 0, n);
                        }
                        out.flush();
                        in.close();
                        out.close();
                    }
                }

            }
            Window.jl4.setText("完成!");
            Window.but1.setEnabled(true);
            Window.text.setText("");
            Window.text1.setText("");
            Window.text1.setEnabled(false);
            Window.but2.setEnabled(false);
            Window.jComboBox.removeAllItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
