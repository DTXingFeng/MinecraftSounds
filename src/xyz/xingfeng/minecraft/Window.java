package xyz.xingfeng.minecraft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class Window extends JFrame {
    /**
     * 窗口大小
     */
    int fcHeight = 500;
    int fcWidth = 700;

    /**
     * 输入.minecraft所在路径的文本框
     */
    public static JTextField text = new JTextField();
    public static JTextField text1 = new JTextField();

    /**
     * 提示文本
     */
    JLabel jl1 = new JLabel("请输入.minecraft路径:");
    JLabel jl2 = new JLabel("请选择要导出的版本");
    JLabel jl3 = new JLabel("请输入导出路径:");
    public static JLabel jl4 = new JLabel("正在等待导出");

    /**
     * 确认文件路径按钮
     */
    public static JButton but1 = new JButton("确定");
    static JButton but2 = new JButton("导出");

    /**
     * 折叠窗口
     */
    public static JComboBox<String> jComboBox = new JComboBox<>();

    /**
     * 接受返回的数组
     */
    public static ArrayList<String> json = new ArrayList<>();

    public Window(){
        // 获取屏幕宽高
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 设置窗口名称
        this.setTitle("DT_刑风_MINECRAFT原版音效提取器");
        // 设置窗口位置大小
        setBounds(scrSize.width / 2 - fcWidth / 2, scrSize.height / 2 - fcHeight / 2, fcWidth, fcHeight);
        // 是否显示窗口体
        setVisible(true);
        // 窗体不能修改大小
        setResizable(false);
        // 窗体关闭后自动结束后台
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 获取布局
        Container cn = this.getContentPane();
        // 取消布局
        cn.setLayout(null);

        //提示语
        jl1.setBounds(32,24,200,50);
        jl1.setFont(new Font("",Font.BOLD,15));
        cn.add(jl1);
        jl2.setBounds(32,70,200,50);
        jl2.setFont(new Font("",Font.BOLD,15));
        cn.add(jl2);
        jl3.setBounds(32,110,200,50);
        jl3.setFont(new Font("",Font.BOLD,15));
        cn.add(jl3);
        jl4.setBounds(32,160,200,50);
        jl4.setFont(new Font("",Font.BOLD,25));
        cn.add(jl4);

        //路径窗口
        text.setBounds(190,32,400,30);
        cn.add(text);
        text1.setBounds(190,120,400,30);
        cn.add(text1);
        text1.addKeyListener(new text1());
        text1.setEnabled(false);

        //确认按钮
        but1.setBounds(598,32,70,30);
        cn.add(but1);
        but1.addActionListener(new but1());
        but2.setBounds(330,70,100,40);
        cn.add(but2);
        but2.setEnabled(false);
        but2.addActionListener(new but2());

        jComboBox.setBounds(190,70,130,40);
        jComboBox.setBackground(Color.WHITE);
        cn.add(jComboBox);

    }

    class but1 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            but1.setEnabled(false);
            text.setEnabled(false);
            File file = new File(text.getText());
            if (file.exists()){
                new IsMinecraft(text.getText());
                for(int i = 0;i < json.size(); i++){
                    jComboBox.addItem(json.get(i));
                }
                text1.setEnabled(true);
            }else {
                JOptionPane.showOptionDialog(null,"请输入正确的地址",
                        "错误",JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE,
                        null,new String[]{"确定"},"确定");
                but1.setEnabled(true);
                text.setEnabled(true);
            }
        }
    }

    class but2 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            but2.setEnabled(false);
            Thread thread = new Thread(new Sounds(jComboBox.getSelectedItem().toString()));
            Window.jl4.setText("正在读取文件");
            thread.start();
        }
    }

    class text1 implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (new File(text1.getText()).exists()){
                but2.setEnabled(true);
            }else {
                but2.setEnabled(false);
            }
        }
    }
}
