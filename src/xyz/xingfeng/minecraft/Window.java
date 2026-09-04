package xyz.xingfeng.minecraft;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class Window extends JFrame {
    /**
     * 窗口大小
     */
    int fcHeight = 500;
    int fcWidth = 700;

    /**
     * 输入.minecraft所在路径、导出路径的文本框
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
     * 确认文件路径按钮、导出按钮
     */
    public static JButton but1 = new JButton("确定");
    public static JButton but2 = new JButton("导出");

    /**
     * 版本下拉框
     */
    public static JComboBox<String> jComboBox = new JComboBox<>();

    public Window() {
        // 设置窗口名称
        this.setTitle("DT_刑风_MINECRAFT原版音效提取器");
        // 设置窗口大小并居中
        setSize(fcWidth, fcHeight);
        setLocationRelativeTo(null);
        // 窗体不能修改大小
        setResizable(false);
        // 窗体关闭后自动结束后台
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 获取布局
        Container cn = this.getContentPane();
        // 取消布局
        cn.setLayout(null);

        //提示语
        jl1.setBounds(32, 24, 200, 50);
        jl1.setFont(new Font("", Font.BOLD, 15));
        cn.add(jl1);
        jl2.setBounds(32, 70, 200, 50);
        jl2.setFont(new Font("", Font.BOLD, 15));
        cn.add(jl2);
        jl3.setBounds(32, 110, 200, 50);
        jl3.setFont(new Font("", Font.BOLD, 15));
        cn.add(jl3);
        jl4.setBounds(32, 160, 640, 50);
        jl4.setFont(new Font("", Font.BOLD, 25));
        cn.add(jl4);

        //路径窗口
        text.setBounds(190, 32, 400, 30);
        cn.add(text);
        text1.setBounds(190, 120, 400, 30);
        cn.add(text1);
        text1.getDocument().addDocumentListener(new ExportPathListener());
        text1.setEnabled(false);

        //确认按钮
        but1.setBounds(598, 32, 70, 30);
        cn.add(but1);
        but1.addActionListener(new but1());
        but2.setBounds(330, 70, 100, 40);
        cn.add(but2);
        but2.setEnabled(false);
        but2.addActionListener(new but2());

        jComboBox.setBounds(190, 70, 130, 40);
        jComboBox.setBackground(Color.WHITE);
        cn.add(jComboBox);

        // 组件添加完毕后再显示窗口
        setVisible(true);
    }

    /**
     * 「确定」：扫描 .minecraft/assets/indexes，填充版本下拉框
     */
    class but1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            but1.setEnabled(false);
            text.setEnabled(false);
            jComboBox.removeAllItems();

            String mcPath = text.getText().trim();
            List<String> versions = IsMinecraft.scan(mcPath);
            if (versions.isEmpty()) {
                JOptionPane.showOptionDialog(null, "该路径下没有找到任何版本资源索引，请确认输入的是 .minecraft 目录，且启动器已下载过游戏资源",
                        "错误", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, new String[]{"确定"}, "确定");
            } else {
                for (String version : versions) {
                    jComboBox.addItem(version);
                }
                text1.setEnabled(true);
            }
            but1.setEnabled(true);
            text.setEnabled(true);
        }
    }

    /**
     * 「导出」：在后台线程执行复制，避免卡住界面
     */
    class but2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object version = jComboBox.getSelectedItem();
            if (version == null) {
                JOptionPane.showOptionDialog(null, "请先选择要导出的版本",
                        "提示", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new String[]{"确定"}, "确定");
                return;
            }
            but1.setEnabled(false);
            text.setEnabled(false);
            but2.setEnabled(false);
            jl4.setText("正在读取文件");
            new Thread(new Sounds(text.getText().trim(), version.toString(), text1.getText().trim())).start();
        }
    }

    /**
     * 导出路径变化时，路径为已存在的文件夹才点亮「导出」按钮
     */
    class ExportPathListener implements DocumentListener {

        private void check() {
            but2.setEnabled(new File(text1.getText().trim()).isDirectory());
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            check();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            check();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            check();
        }
    }
}
