# MinecraftSounds

[![Release](https://img.shields.io/github/v/release/DTXingFeng/MinecraftSounds)](https://github.com/DTXingFeng/MinecraftSounds/releases)
[![License](https://img.shields.io/github/license/DTXingFeng/MinecraftSounds)](./LICENSE)
[![Java](https://img.shields.io/badge/Java-8%2B-orange)](https://www.java.com/)

**Minecraft 原版音效提取器** —— 一个用 Java（Swing）编写的图形界面小工具，可以从本地 `.minecraft` 目录中一键提取（还原）任意版本的全部原版音效文件（`.ogg`），导出后的目录结构与官方资源包一致，可直接用于制作资源包（Resource Pack）、音效包或剪辑素材。

**Minecraft Vanilla Sounds Extractor** — A Java Swing GUI tool that extracts all vanilla Minecraft sound files (`.ogg`) from your local `.minecraft` assets directory. Exported files keep the original resource-pack folder structure, ready for resource-pack creation, sound packs, or editing projects.

---

## 功能特性 / Features

- **中文**：
  - 图形界面（Java Swing），无需命令行，开箱即用
  - 自动扫描 `.minecraft/assets/indexes`，列出本机已下载的所有客户端版本（1.8 ~ 最新版）
  - 按资源索引（asset index）逐一还原音效文件的真实路径与文件名
  - 导出目录结构与资源包一致（如 `minecraft/sounds/dig/stone1.ogg`），可直接二次利用
  - 支持 Windows / macOS / Linux（仅需安装 Java 运行环境）
- **English**:
  - Swing-based GUI — no command line needed
  - Auto-detects every client version downloaded in `.minecraft/assets/indexes` (1.8 – latest)
  - Restores real file paths and names from the asset index
  - Output keeps the resource-pack layout (e.g. `minecraft/sounds/dig/stone1.ogg`)
  - Cross-platform (Windows / macOS / Linux) — only requires a Java runtime

## 使用方法 / Usage

1. 从 [Releases](https://github.com/DTXingFeng/MinecraftSounds/releases) 下载最新的 `MinecraftSounds-*.jar`；
2. 确保已安装 **Java 8 或更高版本**（[下载地址](https://www.java.com/download/)）；
3. 双击运行，或在命令行执行：

   ```bash
   java -jar MinecraftSounds-v1.1.jar
   ```

4. 在第一个输入框填写 `.minecraft` 目录路径（例如 `C:\Users\<你>\AppData\Roaming\.minecraft`）并点击「确定」；
5. 在下拉框中选择要导出的游戏版本；
6. 填写导出目标文件夹（需已存在），点击「导出」，等待进度显示「完成!」即可。

> English users: fill in your `.minecraft` path, press the left button, pick a version from the dropdown, type an existing export folder, then press the export button (「导出」). The progress label shows `完成!` when finished.

视频教程 / Video tutorial (中文)：[Bilibili BV1nY4y1c76h](https://www.bilibili.com/video/BV1nY4y1c76h/)

## 工作原理 / How it works

Minecraft 1.8+ 将所有资源按 SHA-1 哈希存放在 `assets/objects/` 中，文件名即哈希值；`assets/indexes/<版本>.json` 记录了每个资源的原始路径与哈希的对应关系。本程序读取该索引，把音效文件从 `objects/<前两位>/<哈希>` 复制出来，并按原始路径重命名还原。

Minecraft 1.8+ stores every asset in `assets/objects/` named by its SHA-1 hash, while `assets/indexes/<version>.json` maps each asset's real path to its hash. This tool reads that index, copies each sound file out of `assets/objects/`, and restores its original path and name.

## 版本选择与兼容性 / Version notes & compatibility

- ✅ Minecraft **1.8** 及之后的版本；
- ❌ 1.8 之前的版本（音效的存储方式与现在不同，暂不支持）；
- ⚠️ **从 1.19.3（22w42a）开始**，散列资源文件的版本命名改为自增的编号，因此本软件下拉框中显示的「版本号」**并不准确**。版本详情可参考 [Minecraft Wiki：散列资源文件](https://zh.minecraft.wiki/w/%E6%95%A3%E5%88%97%E8%B5%84%E6%BA%90%E6%96%87%E4%BB%B6)。
  English: since 1.19.3 (22w42a) asset-index files use incremental numbers instead of version names, so the "version" shown in the dropdown may not match the game version exactly. See [Minecraft Wiki: hashed client assets](https://zh.minecraft.wiki/w/%E6%95%A3%E5%88%97%E8%B5%84%E6%BA%90%E6%96%87%E4%BB%B6) for details.

## 从源码构建 / Build from source

```bash
git clone https://github.com/DTXingFeng/MinecraftSounds.git
cd MinecraftSounds
mkdir build && javac -d build src/Main.java src/xyz/xingfeng/minecraft/*.java
jar cfm MinecraftSounds.jar src/META-INF/MANIFEST.MF -C build .
```

## 常见问题 / FAQ

- **下拉框里没有我想要的版本？**
  请先启动一次对应版本的 Minecraft 让启动器下载资源，或使用启动器预先下载该版本的资源文件，再运行本工具。
- **导出按钮是灰色的？**
  导出路径必须是一个已经存在的文件夹，输入正确路径后按钮会自动点亮。
- **提取出来的文件能商用吗？**
  音效版权归 Mojang 所有，导出内容仅建议用于个人学习、Mod / 资源包开发等合理用途，请遵守 Minecraft EULA。

## 许可证 / License

本项目代码以 [MIT License](./LICENSE) 开源。Minecraft 及其音效资源版权归 Mojang Studios 所有，本项目与 Mojang / Microsoft 无关联。

---

## Stargazers over time

[![Stargazers over time](https://starchart.cc/DTXingFeng/MinecraftSounds.svg?variant=adaptive)](https://starchart.cc/DTXingFeng/MinecraftSounds)
