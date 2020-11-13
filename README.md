[English](README.md)  [简体中文](README.CN.md)

# Energy Level Transition 
![Build](https://github.com/MoegTech/EnergyLevelTransition/workflows/Build/badge.svg) 
[![Discord](https://img.shields.io/badge/Discord-Join%20Us-blue)](https://discord.gg/BWn6E94)
[![QQ Group](https://img.shields.io/badge/QQ%20Group-940209097-blue)](https://jq.qq.com/?_wv=1027&k=keVW7jBX)

![LOGO](https://raw.githubusercontent.com/MoegTech/EnergyLevelTransition/1.16/src/main/resources/logos/logo-300-300.png)

This is the official repository of the Minecraft Mod - Energy Level Transition on Minecraft 1.16 and beyond

Requires: [Fabric API](https://github.com/FabricMC/fabric) 
and Fabric Tutorial: https://fabricmc.net/wiki/tutorial:setup

- [Energy Level Transition](#energy-level-transition)
- [License](#license)
- [Introduction](#introduction)
- [Acknowledgement](#acknowledgement)
- [Dependencies](#dependencies)
- [Setup Dev Environment](#setup-dev-environment)
- [Updating Minecraft](#updating-minecraft)

## Original Setting
- ELT II: https://tieba.baidu.com/p/3491285047
- ELT IIS: https://tieba.baidu.com/p/5858060400
- ELT Wiki: https://energy-level-transition.fandom.com/zh/wiki/Fraxinus_In_A_Gale

## License

All modules of this Mod starting with "elt" are licensed under [GNU General Public License Version 3](LICENSE). 

All assets, unless otherwise stated, are dedicated to the public domain
according to the [ELT Assets License](src/main/resources/LICENSE.assets).

Any assets containing the [ELT logos](src/main/resources/assets.energyleveltransition/icon.png) or any
derivative of it are licensed under the [Creative Commons Attribution-NonCommercial 4.0 International Public License](src/main/resources/LICENSE.logos).

We maintain a fork of [Advanced Runtime Resource Pack](https://github.com/Devan-Kerman/ARRP) 
under [Mozilla Public License Version 2.0](arrp/LICENSE)

## Introduction

A Tech Mod that brings in Science, Etherology, and Magic!

## Acknowledgement

[Lyuuke](https://github.com/Lyuuke) for creating some Assets and Ideas for this Mod. 

## Dependencies

- Cardinal Components API
- Lib GUI
- Lib Block Attributes

Users don't need to download them separately since they are nested in this mod. 

## Setup Dev Environment

If you are using Windows, use the following style

```gradlew <task>```

or if you're using MacOSX/Linux/Unix:

```./gradlew <task>```

1. Firstly, clone ELT Project by executing `git clone https://github.com/MoegTech/EnergyLevelTransition.git` under your root directory. 

2. Rename your working directory into `ELT_Workspace` (not arbitrary), we call it the root directory. 

3. Then, Use IntelliJ or Eclipse to open `build.gradle` file in the root directory, and choose`import as project`. (Eclipse users might need to run `gradlew eclipse` after importing it)

4. Once Gradle is synced, you succeed partially!

5. Run `gradlew genSources` under root directory to generate Minecraft sources for reference. 

6. Run `gradlew build` under root directory to build artifacts in both `./ELT_Workspace/build/libs`, `./ELT_Workspace/elt/build/libs`, and `./ELT_Workspace/eltcore/build/libs`.

7. Run `gradlew :elt:runClient` to run Minecraft Client with entire ELT.

For more setup instructions or questions encountered please check the [fabric setup tutorial](https://fabricmc.net/wiki/tutorial:setup) that relates to the IDE that you are using.

## Updating Minecraft

When we update the environment for a newer MC version, we need to follow the steps below.

Firstly, change the version variables in `gradle.properties`. For example:

```
# Fabric Properties: see updates here: https://modmuss50.me/fabric.html
 minecraft_version=1.16.4
 yarn_mappings=1.16.4+build.6
 loader_version=0.10.6+build.214
 fabric_version=0.25.1+build.416-1.16
```

Secondly, run `gradlew :elt:migrateMappings <yarn_mappings>` and `gradlew :eltcore:migrateMappings <yarn_mappings>` 
You will get `remappedSrc` directory under `:elt` and `:eltcore`. Use the `remappedSrc/com` to replace the existing `src/main/java/com` directory in both `:elt` and `:eltcore`

Thirdly, run `gradlew build` and `gradlew :elt:runClient` normally to see if it works well. 

If you encounter problems, try
Run `gradlew --refresh-dependencies` to refresh dependencies or
run `gradlew cleanLoom` to clean Loom cache. Delete the `{%USER-HOME%}/.gradle/caches/fabric-loom` folder if the you really screwed up.
