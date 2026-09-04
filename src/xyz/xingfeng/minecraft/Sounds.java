package xyz.xingfeng.minecraft;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取资源索引，把 assets/objects 里按 SHA-1 哈希存放的音效复制回原始路径
 */
public class Sounds implements Runnable {

    /**
     * 匹配索引里形如 "minecraft/sounds/xxx": {"hash": "40位十六进制", ...} 的音效条目
     */
    private static final Pattern SOUND_ENTRY = Pattern.compile(
            "\"([^\"]*minecraft/sounds[^\"]*)\"\\s*:\\s*\\{[^{}]*?\"hash\"\\s*:\\s*\"([0-9a-fA-F]{40})\"");

    private final Path indexFile;
    private final Path objectsDir;
    private final Path exportDir;

    public Sounds(String mcRoot, String version, String exportPath) {
        this.indexFile = Paths.get(mcRoot, "assets", "indexes", version + ".json");
        this.objectsDir = Paths.get(mcRoot, "assets", "objects");
        this.exportDir = Paths.get(exportPath);
    }

    @Override
    public void run() {
        try {
            export();
            setProgress("完成!");
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage() == null ? e.toString() : e.getMessage();
            SwingUtilities.invokeLater(() -> {
                Window.jl4.setText("导出失败");
                JOptionPane.showMessageDialog(null, "导出失败：" + message, "错误", JOptionPane.ERROR_MESSAGE);
            });
        } finally {
            // 无论成功失败都恢复界面，让用户可以直接重试
            SwingUtilities.invokeLater(() -> {
                Window.text.setEnabled(true);
                Window.but1.setEnabled(true);
                Window.text1.setText("");
                Window.but2.setEnabled(false);
            });
        }
    }

    private void export() throws IOException {
        String json = new String(Files.readAllBytes(indexFile), StandardCharsets.UTF_8);

        List<String> names = new ArrayList<>();
        List<String> hashes = new ArrayList<>();
        Matcher matcher = SOUND_ENTRY.matcher(json);
        while (matcher.find()) {
            names.add(matcher.group(1));
            hashes.add(matcher.group(2));
        }

        int skipped = 0;
        for (int i = 0; i < names.size(); i++) {
            setProgress(i + "/" + names.size());
            Path source = objectsDir.resolve(hashes.get(i).substring(0, 2)).resolve(hashes.get(i));
            if (!Files.isRegularFile(source)) {
                // 该资源尚未被启动器下载
                skipped++;
                continue;
            }
            Path target = exportDir.resolve(names.get(i));
            Files.createDirectories(target.getParent());
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
        setProgress(skipped > 0 ? "完成! (跳过 " + skipped + " 个未下载的文件)" : "完成!");
    }

    /**
     * Swing 组件只能在事件调度线程上更新
     */
    private void setProgress(String s) {
        SwingUtilities.invokeLater(() -> Window.jl4.setText(s));
    }
}
