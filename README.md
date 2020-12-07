[English](README.md) | [Русский](README.RU.md) | [简体中文](README.CN.md)

# Energy Level Transition 
![Build](https://github.com/MoegTech/EnergyLevelTransition/workflows/Build/badge.svg) 
[![Discord](https://img.shields.io/badge/Discord-Join%20Us-blue)](https://discord.gg/BWn6E94)
[![QQ Group](https://img.shields.io/badge/QQ%20Group-940209097-blue)](https://jq.qq.com/?_wv=1027&k=keVW7jBX)

![LOGO](https://raw.githubusercontent.com/MoegTech/EnergyLevelTransition/1.16/src/main/resources/logos/logo-300-300.png)

This is the official repository of the Minecraft Mod - Energy Level Transition on Minecraft 1.16 and beyond

Requires: [MinecraftForge](https://github.com/MinecraftForge/MinecraftForge) 

- [Energy Level Transition](#energy-level-transition)
- [Introduction](#introduction)
- [Background](#background)
- [License](#license)
- [Acknowledgement](#acknowledgement)
- [Dependencies](#dependencies)
- [Setup Dev Environment](#setup-dev-environment)

## Introduction

WIP

## Background

ELT gains some inspiration from the following posts created by Lyuuke(https://github.com/Lyuuke)
- ELT II: https://tieba.baidu.com/p/3491285047
- ELT IIS: https://tieba.baidu.com/p/5858060400
- ELT Wiki: https://energy-level-transition.fandom.com/zh/wiki/Fraxinus_In_A_Gale

## License

All modules of this Mod with Mod-ID starting with "elt" are licensed under [GNU General Public License Version 3](LICENSE). 

All assets, unless otherwise stated, are dedicated to the public domain
according to the [ELT Assets License](src/main/resources/LICENSE.assets).

Any assets containing the [ELT logos](src/main/resources/assets.energyleveltransition/icon.png) or any
derivative of it are licensed under the [Creative Commons Attribution-NonCommercial 4.0 International Public License](src/main/resources/LICENSE.logos).

## Acknowledgement

[Lyuuke](https://github.com/Lyuuke) for creating some Assets and Ideas for this Mod. 

## Dependencies

TBA

## Setup Dev Environment

If you are using Windows, use the following style

```gradlew <task>```

or if you're using MacOSX/Linux/Unix:

```./gradlew <task>```

1. Firstly, clone ELT Project by executing `git clone https://github.com/MoegTech/EnergyLevelTransition.git` under your root directory. 

2. Rename your working directory into `ELT_Workspace` (name is arbitrary), we call it the root directory. 

3. Then, Use IntelliJ or Eclipse to open `build.gradle` file in the root directory, and choose`import as project`.

4. Once Gradle is synced, you succeed partially!

5. Run `gradlew genIntellijRuns` or `gradlew genEclipseRuns` under root directory to generate necessary files.

6. Run `gradlew build` under root directory to build artifacts in `./ELT_Workspace/build/libs`

7. Run `gradlew runClient` to run Minecraft Client with the Mod.

For more setup instructions or questions encountered please check the [Forge Docs](https://mcforge.readthedocs.io/en/1.16.x/gettingstarted/) that relates to the IDE that you are using.