Other Languages: [English](README.md) | [简体中文](README.CN.md)

# 能级跃迁(Energy Level Transition)

![构建状态](https://github.com/MoegTech/EnergyLevelTransition/workflows/Build/badge.svg) 
[![Discord - https://discord.gg/BWn6E94](https://img.shields.io/badge/Discord-Join%20Us-blue)](https://discord.gg/BWn6E94)
[![QQ群 - 940209097](https://img.shields.io/badge/QQ%E7%BE%A4-940209097-blue)](https://jq.qq.com/?_wv=1027&k=keVW7jBX)

![LOGO](https://raw.githubusercontent.com/MoegTech/EnergyLevelTransition/1.16/src/main/resources/logos/logo-300-300.png)

这是 Minecraft 模组 - 能级跃迁(Energy Level Transition)的官方仓库，适用于 Minecraft 1.16+ .

- [能级跃迁](#能-级-跃-迁)
- [协议](#协议)
- [导言](#导言)
- [致谢](#致谢)
- [依赖](#依赖)
- [设置开发环境](#设置开发环境)
- [更新Minecraft](#更新Minecraft)

## 协议

本模组的所有以 `elt` 开头的模块均使用 [GNU General Public License Version 3](LICENSE) 授权。

除非另有说明，所有的资源文件
均根据 [ELT 资产许可证](src/main/resources/LICENSE.assets) 进行许可。

任何包含 [ELT 徽标](src/main/resources/assets.energyleveltransition/icon.png) 的资源文件及任何衍生品
均根据协议 [CC-BY-NC 4.0](src/main/resources/LICENSE.logos) 进行许可。

我们维护了一个[Advanced Runtime Resource Pack](https://github.com/Devan-Kerman/ARRP)的分支，
采用 [Mozilla Public License Version 2.0](arrp/LICENSE) 授权。
  
## 导言

这是一个带来科学、以太学和魔法的科技模组!

## 致谢

[Lyuuke](https://github.com/Lyuuke)，为这个模组贡献了图像等资源文件和他的创意.

## 依赖

- Cardinal Components API
- Lib GUI
- Lib Block Attributes

作为使用者, 你将不需要单独下载它们，因为它们已经被包含在本模组中。

## 设置开发环境

如果你在使用 Windows，使用以下命令行运行 Gradle:

```gradlew <task>```

或者你在使用 Linux/Unix/MacOS (Unix like):

```./gradlew <task>```

1. 首先，在根目录下执行`git clone https://github.com/MoegTech/EnergyLevelTransition.git`克隆本项目。

2. 将你的工作目录重命名为`ELT_Workspace`（不是任意命名），我们称之为根目录。

3. 然后，用 IntelliJ IDEA 或 Eclipse 打开根目录下的`build.gradle`文件，选择`import as project`。(Eclipse用户导入后可能需要运行`gradlew eclipse`)

4. 如果执行成功, 你就可以继续下一步, 恭喜.

5. 在根目录下运行`gradlew genSources`，生成 Minecraft 源码以供开发需要

6. 在根目录下运行`gradlew build`，在目录 `./ELT_Workspace/build/libs`、`./ELT_Workspace/elt/build/libs` 和 `./ELT_Workspace/eltcore/build/libs` 下可以找到构建产物。

7. 运行 `gradlew :elt:runClient` 来运行包含有 ELT 的 Minecraft 客户端。

对于更多的设置说明或遇到的问题，请检查 [Fabric 设置教程](https://fabricmc.net/wiki/tutorial:setup)，涉及到你正在使用的IDE。

## 更新 Minecraft

有时，Minecraft 的版本在 `gradle.properties` 中会发生变化。

在使用新版本构建之前，运行 `gradlew migrateMappings <version>` 以获得 `remappedSrc` 文件夹。使用重映射的源文件替换现有的 `src` 文件夹。
 
如果出错，运行 `gradlew --refresh-dependencies` 来刷新依赖关系。

运行 `gradlew cleanLoom` 来清理 Loom 缓存。

如果你真的搞砸了，请删除 `{%USER-HOME%}/.gradle/caches/fabric-loom` 文件夹。
