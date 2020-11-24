[English](README.md) [Русский](README.RU.md)  [简体中文](README.CN.md)

# Energy Level Transition 
![Build](https://github.com/MoegTech/EnergyLevelTransition/workflows/Build/badge.svg) 
[![Discord](https://img.shields.io/badge/Discord-Join%20Us-blue)](https://discord.gg/BWn6E94)
[![QQ Group](https://img.shields.io/badge/QQ%20Group-940209097-blue)](https://jq.qq.com/?_wv=1027&k=keVW7jBX)

![LOGO](https://raw.githubusercontent.com/MoegTech/EnergyLevelTransition/1.16/src/main/resources/logos/logo-300-300.png)

Это официальный репозиторий Minecraft Мода — Energy Level Transition на версии Minecraft 1.16 и выше

Требования: [Fabric API](https://github.com/FabricMC/fabric) 
и Fabric туториал: https://fabricmc.net/wiki/tutorial:setup

- [Energy Level Transition](#energy-level-transition)
- [Лицензия](#лицензия)
- [Введение](#введение)
- [Благодарности](#благодарности)
- [Использование](#использование)
- [Зависимости](#зависимости)
- [Настройка среды разработки](#настройка-среды-разработки)
- [Обновление Minecraft](#обновление-minecraft)

## Исходные настройки
- ELT II: https://tieba.baidu.com/p/3491285047
- ELT IIS: https://tieba.baidu.com/p/5858060400
- ELT Wiki: https://energy-level-transition.fandom.com/zh/wiki/Fraxinus_In_A_Gale

## Лицензия

Все модули этого мода, начинающиеся с "elt" лицензированы под GNU [General Public License Version 3](LICENSE). 

Все ресурсы, если не указано другое, переданы в общественное достояние
исходя из [ELT Assets License](src/main/resources/LICENSE.assets).

Все ресурсы, содержащие [ELT лого](src/main/resources/assets.energyleveltransition/icon.png) или их производные
лицензированы под [Creative Commons Attribution-NonCommercial 4.0 International Public License](src/main/resources/LICENSE.logos).

Мы поддерживаем форк [Advanced Runtime Resource Pack](https://github.com/Devan-Kerman/ARRP) 
под лицензией [Mozilla Public License Version 2.0](arrp/LICENSE)

## Введение

Технический мод, который приносит в мир Науку, Etherology (термин не переводится), и Магию!

## Благодарности

[Lyuuke](https://github.com/Lyuuke) за создание некоторых ресурсов и идеи для этого мода.

## Использование

Добавьте это в ваш `build.gradle` для использования мода ELT как зависимости
### Используйте всё вместе...
```
dependencies {
	modImplementation("com.teammoeg:Energy-Level-Transition:0.02.05") {exclude module: "log4j-core"}
 }
 ```
### ...или по отдельности
```
dependencies {
	modImplementation("com.teammoeg:elt:0.02.05") {exclude module: "log4j-core"}
	modImplementation("com.teammoeg:eltcore:0.02.05") {exclude module: "log4j-core"}
}
```

## Зависимости

- Cardinal Components API
- Lib GUI
- Lib Block Attributes

Пользователям мода не нужно скачивать их отдельно, так как они вложены в этом моде.

## Настройка среды разработки

Если вы используете Windows, пишите следующее

```gradlew <задача>```

или если вы используете MacOS или GNU/Linux:

```./gradlew <задача>```

1. Во–первых, склонируйте ELT Project с помощью `git clone https://github.com/MoegTech/EnergyLevelTransition.git` в вашей главной папке. 

2. Переименуйте вашу рабочую папку в `ELT_Workspace` (не любое другое), мы называем её главной папкой. 

3. Затем, используйте IntelliJ или Eclipse для открытия `build.gradle`, и выберите `import as project`. (Пользователи Eclipse также необходимо запустить `gradlew eclipse` после импорта)

4. Как только Gradle будет синхронизирован, вы почти настроили!

5. Запустите `gradlew genSources` в главной папке, чтобы сгенерировать исходный код Minecraft для справки.

6. Запустите `gradlew build` в главной папке для сборки мода в папках `./ELT_Workspace/build/libs`, `./ELT_Workspace/elt/build/libs`, и `./ELT_Workspace/eltcore/build/libs`.

7. Запустите `gradlew :elt:runClient` для запуска Minecraft со всем комплектом ELT.

Если вы хотите больше инструкций или у вас есть вопросы по настройке, проверьте [туториал по настройке от fabric](https://fabricmc.net/wiki/tutorial:setup).

## Обновление Minecraft

Когда мы обновляем среду на новую MC версию, нам необходимо следовать следующим этапам.

Во–первых, измените переменную с версией в `gradle.properties`. Например:

```
# Fabric Properties: обновления здесь: https://modmuss50.me/fabric.html
 minecraft_version=1.16.4
 yarn_mappings=1.16.4+build.7
 loader_version=0.10.8
 fabric_version=0.26.2+1.16
```

Во–вторых, запустите `gradlew :elt:migrateMappings <yarn_mappings>` и `gradlew :eltcore:migrateMappings <yarn_mappings>` 
У вас появится каталог `remappedSrc` с `:elt` и `:eltcore`. Замените папкой `remappedSrc/com` папку `src/main/java/com` в `:elt` и `:eltcore`

В–третьих, запустите `gradlew build` и `gradlew :elt:runClient` и проверьте работу. 

Если у вас появятся проблемы, попробуйте:
Запустите `gradlew --refresh-dependencies` для обновления зависимостей или
запустите `gradlew cleanLoom` для очистки Loom кэша. Удалите папку `{%USER-HOME%}/.gradle/caches/fabric-loom` если вы хотите быть уверены.
