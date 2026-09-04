import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 生成应用图标：深色圆角底 + 像素风音符（256x256，PNG 载荷的 ICO）
 */
public class MakeIcon {
    public static void main(String[] args) throws Exception {
        int s = 256;
        int p = s / 16;
        BufferedImage img = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(0x21, 0x1F, 0x1E));
        g.fillRoundRect(0, 0, s, s, 40, 40);
        g.setColor(new Color(0x5E, 0xAA, 0x46));
        // 音符符头（2x2 像素块）
        g.fillRect(7 * p, 13 * p, 2 * p, 2 * p);
        g.fillRect(6 * p, 14 * p, p, p);
        // 符杆
        g.fillRect(9 * p, 5 * p, p, 8 * p);
        // 符尾
        g.setColor(new Color(0x83, 0xC9, 0x5C));
        g.fillRect(10 * p, 5 * p, 2 * p, p);
        g.fillRect(11 * p, 6 * p, p, 2 * p);
        g.dispose();
        ImageIO.write(img, "png", new File(args[0] + "/icon.png"));

        // ICO 容器（6 字节头 + 16 字节目录项 + PNG 数据），多字节字段一律小端序
        ByteArrayOutputStream png = new ByteArrayOutputStream();
        try (FileInputStream in = new FileInputStream(args[0] + "/icon.png")) {
            byte[] b = new byte[8192];
            int n;
            while ((n = in.read(b)) != -1) png.write(b, 0, n);
        }
        java.nio.ByteBuffer hdr = java.nio.ByteBuffer.allocate(22).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        hdr.putShort((short) 0);          // reserved
        hdr.putShort((short) 1);          // type: icon
        hdr.putShort((short) 1);          // 图像数量
        hdr.put((byte) 0);                // 宽 256 编码为 0
        hdr.put((byte) 0);                // 高 256 编码为 0
        hdr.put((byte) 0);                // 调色板色数
        hdr.put((byte) 0);                // 保留
        hdr.putShort((short) 1);          // 颜色平面数
        hdr.putShort((short) 32);         // 位深
        hdr.putInt(png.size());           // 图像数据长度
        hdr.putInt(22);                   // 图像数据偏移
        try (java.io.FileOutputStream out = new java.io.FileOutputStream(args[0] + "/icon.ico")) {
            out.write(hdr.array());
            png.writeTo(out);
        }
    }
}
