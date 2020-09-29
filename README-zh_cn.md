[English](README.md)  [简体中文](README-zh_cn.md)

# 能级跃迁 ![Build](https://github.com/MoegTech/EnergyLevelTransition/workflows/ELT%20Snapshot%20Build/badge.svg) [![Join our Discord!](https://img.shields.io/badge/Discord-Join%20Us-blue)](https://discord.gg/BWn6E94)

这是 Minecraft 模组 - 能级跃迁(Energy Level Transition)的官方仓库，使用于Minecraft 1.16+

- [能级跃迁](#能级跃迁)
- [协议](#协议)
- [导言](#导言)
- [依赖](#依赖)
- [设置开发环境](#设置开发环境)
- [更新Minecraft](#更新Minecraft)

## 协议

这是一个以 [GNU通用公共许可证第3版](https://www.gnu.org/licenses/) 授权的开源mod。
  
## 导言

这是一个科技MOD--通过隐藏的魔法元素来展现现实主义--通过世界生成、自然环境、玩家属性等等。

## 依赖

- Advanced Runtime Resource Pack
- Cardinal Components API
- Lib GUI
- Lib Block Attributes

用户不需要单独下载它们，因为它们被嵌套在这个mod中。

## 设置开发环境

如果你在使用 Windows，使用以下格式运行Gradle任务

```gradlew <task>```

或者你在使用 Linux/Unix/MacOS:

```./gradlew <task>```

1. 首先，在根目录下执行`git clone https://github.com/MoegTech/EnergyLevelTransition.git`克隆ELT项目。

2. 将你的工作目录重命名为`ELT_Workspace`（不是任意命名），我们称之为根目录。

3. 然后，用IntelliJ或Eclipse打开根目录下的`build.gradle`文件，选择`import as project`。(Eclipse用户导入后可能需要运行`gradlew eclipse`)

4. 一旦Gradle同步了，你就成功了一部分!

5. 在根目录下运行`gradlew genSources`，生成Minecraft源码供参考。

6. 6.在根目录下运行`gradlew build`，在`./ELT_Workspace/build/libs`、`./ELT_Workspace/elt/build/libs`和`./ELT_Workspace/eltcore/build/libs`中构建工件。

7. 运行`gradlew :elt:runClient`来运行Minecraft客户端与整个ELT。

对于更多的设置说明或遇到的问题，请检查[fabric设置教程](https://fabricmc.net/wiki/tutorial:setup)，涉及到你正在使用的IDE。

##更新Minecraft

有时，MC的版本在`gradle.properties`中会发生变化。

在使用新版本构建之前，运行`gradlew migrateMappings <version>`以获得`remappedSrc`文件夹。使用重映射的源文件替换现有的`src`文件夹。
 
如果出错，运行`gradlew --refresh-dependencies`来刷新依赖关系。

运行`gradlew cleanLoom`来清理Loom缓存。

如果你真的搞砸了，请删除`{%USER-HOME%}/.gradle/caches/fabric-loom`文件夹。