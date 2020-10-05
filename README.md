[English](README.md)  [简体中文](README.CN.md)

# Energy Level Transition ![Build](https://github.com/MoegTech/EnergyLevelTransition/workflows/Build/badge.svg) [![Join our Discord!](https://img.shields.io/badge/Discord-Join%20Us-blue)](https://discord.gg/BWn6E94)

This is the official repository of the Minecraft Mod - Energy Level Transition on Minecraft 1.16 and beyond

Requires: [Fabric API](https://github.com/FabricMC/fabric)

- [Energy Level Transition 1.16](#energy-level-transition-116)
- [License](#license)
- [Introduction](#introduction)
- [Dependencies](#dependencies)
- [Setup Dev Environment](#setup-dev-environment)
- [Updating Minecraft](#updating-minecraft)

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

Sometimes, MC version changes in `gradle.properties`. 

Run `gradlew migrateMappings <version>` before building with the new version to get `remappedSrc` folder. Use the remapped source to replace the existing `src` folder. 
 
Run `gradlew --refresh-dependencies` to refresh dependencies if anything went wrong.

Run `gradlew cleanLoom` to clean Loom cache. 

Delete the `{%USER-HOME%}/.gradle/caches/fabric-loom` folder if the you really screwed up.
