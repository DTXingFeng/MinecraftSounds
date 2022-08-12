package xyz.xingfeng.minecraft;

import javax.swing.*;
import java.io.File;

public class IsMinecraft{
    String path ;
    public IsMinecraft(String path){
        this.path = path + "\\assets\\indexes";
        File file = new File(this.path);
        for (File t : file.listFiles()) {
            if (t.isFile()) {
                Window.json.add(t.getName().replaceAll(".json",""));
            }
        }
    }
}
