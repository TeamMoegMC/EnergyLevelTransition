Происхождение[English](README.md) [Русский](README.RU.md)  [简体中文](README.CN.md)

# Energy Level Transition 
![Build](https://github.com/MoegTech/EnergyLevelTransition/workflows/Build/badge.svg) 
[![Discord](https://img.shields.io/badge/Discord-Join%20Us-blue)](https://discord.gg/BWn6E94)
[![QQ Group](https://img.shields.io/badge/QQ%20Group-940209097-blue)](https://jq.qq.com/?_wv=1027&k=keVW7jBX)

![LOGO](https://raw.githubusercontent.com/MoegTech/EnergyLevelTransition/1.16/src/main/resources/logos/logo-300-300.png)

Это официальный репозиторий Minecraft мода — Energy Level Transition на версии Minecraft 1.16 и выше
Происхождение
Требуется: [MinecraftForge](https://github.com/MinecraftForge/MinecraftForge) 

- [Energy Level Transition](#energy-level-transition)
- [Введение](#введение)
- [Происхождение](#происхождение)
- [Лицензия](#лицензия)
- [Благодарности](#благодарности)
- [Зависимости](#зависимости)
- [Настройка среды разработки](#настройка-среды-разработки)

## Введение

WIP

## Происхождение
Мод ELT вдохновлялся следующими постами, написанные [Lyuuke](https://github.com/Lyuuke)
- ELT II: https://tieba.baidu.com/p/3491285047
- ELT IIS: https://tieba.baidu.com/p/5858060400
- ELT Wiki: https://energy-level-transition.fandom.com/zh/wiki/Fraxinus_In_A_Gale

## Лицензия

Все модули этого мода, чей Mod-ID начинается с "elt", лицензированы под [GNU General Public License Version 3](LICENSE). 

Все ресурсы, если не указано иное, переданы в общественное достояние
следуя [ELT Assets License](src/main/resources/LICENSE.assets).

Все ресурсы, содержащие [ELT логотипы](src/main/resources/assets.energyleveltransition/icon.png) или их производные
лицензированы под [Creative Commons Attribution-NonCommercial 4.0 International Public License](src/main/resources/LICENSE.logos).

## Благодарности

[Lyuuke](https://github.com/Lyuuke) за создание некоторых ресурсов и идей для этого мода.

## Зависимости

TBA

## Настройка среды разработки


Если вы используете Windows, MacOS, Linux, FreeBSD и так далее:

```./gradlew <task>```

- Получение необходимых файлов

    - Для начала склонируйте ELT Project в вашу папку этой командой:
        ```
        git clone https://github.com/MoegTech/EnergyLevelTransition.git
        ```

- Настройка Gradle

    - Затем, с помощью IntelliJ IDEA или Eclipse откройте файл `build.gradle` и выберите `Import as project`

    - Ждите, пока Gradle синхронизируется

    - Запустите `gradlew genIntellijRuns` или `gradlew genEclipseRuns` для автоматического создания необходимых файлов

- Настройка карт обфускации (маппингов)

    - Мы используем карты обфускации, основанные на официальной (от Mojang) карте обфускации, с именами параметров из MCP и Yarn. 
      [Скрипт](https://github.com/alcatrazEscapee/Mappificator) для создания карт обфускации создан [AlcatrazEscapee](https://github.com/alcatrazEscapee)

    - Вы должны установить [Maven](https://maven.apache.org/). (Python задействует mvn с помощью командной строки, так что оно должно находиться в $PATH)
        - Чтобы проверить работоспособность Maven, запустите mvn --version в командной строке

    - Для генерации карт обфускации вам необходимо запустить `src/mappificator.py` лишь один раз

- Запуск и сборка
  
    - Запустите `gradlew build` для сборки мода в `build/libs`

    - Запустите `gradlew runClient` для запуска Minecraft клиента с модом.

Если у вас возникли вопросы или вы хотите больше инструкций, заходите на [Forge Docs](https://mcforge.readthedocs.io)
