/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *   Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.moeg.eltcore;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.feature.StructureFeature;
import net.moeg.eltcore.code.ArrayListNoNulls;
import net.moeg.eltcore.handlers.*;
import net.moeg.eltcore.mixin.GeneratorTypeAccessor;
import net.moeg.eltcore.tag.ELTTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static net.moeg.eltcore.data.CS.F;
import static net.moeg.eltcore.tag.ELTTag.createTag;

public class ELTCORE_Main implements ModInitializer {

    public static final String MOD_ID = "eltcore";
    public static final String MOD_NAME = "ELT Core";
    public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_NAME);
    public static JLang EN_US = JLang.lang();
    public static JLang ZH_CN = JLang.lang();
    public static final RuntimeResourcePack ELTRESOURCE = RuntimeResourcePack.create(MOD_ID + ":main");
    public static final Handler_ItemGroups ITEM_GROUPS_ELT = new Handler_ItemGroups();

    public static FlatChunkGeneratorConfig getDefaultConfig(Registry<Biome> biomeRegistry) {
        StructuresConfig structuresConfig = new StructuresConfig(Optional.of(StructuresConfig.DEFAULT_STRONGHOLD), Maps.newHashMap(ImmutableMap.of(StructureFeature.VILLAGE, StructuresConfig.DEFAULT_STRUCTURES.get(StructureFeature.VILLAGE))));
        FlatChunkGeneratorConfig flatChunkGeneratorConfig = new FlatChunkGeneratorConfig(structuresConfig, biomeRegistry);
        flatChunkGeneratorConfig.biome = () -> {
            return (Biome)biomeRegistry.getOrThrow(BiomeKeys.PLAINS);
        };
        flatChunkGeneratorConfig.getLayers().add(new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        flatChunkGeneratorConfig.getLayers().add(new FlatChunkGeneratorLayer(2, Blocks.DIRT));
        flatChunkGeneratorConfig.getLayers().add(new FlatChunkGeneratorLayer(1, Blocks.BONE_BLOCK));
        flatChunkGeneratorConfig.updateLayerBlocks();
        return flatChunkGeneratorConfig;
    }

    private static final GeneratorType ELTREALISTIC = new GeneratorType("elt_realistic") {
        protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
            return new FlatChunkGenerator(ELTCORE_Main.getDefaultConfig(biomeRegistry));
        }
    };

    public static ELTTag PROTON = createTag(1, "protest", "Proton");

    @Override
    public void onInitialize() {

        LOGGER.info("~~~~~~~~~~~~~ ELT Core Created by TeamMoeg ~~~~~~~~~~~~");
        LOGGER.info("~~~~~~~~~ https://github.com/MoegTech/ELTCore ~~~~~~~~~");

        ArrayListNoNulls<Runnable> tList = new ArrayListNoNulls<>(F,
                new Handler_WorldTypes()
        );
        for (Runnable tRunnable : tList) try {tRunnable.run();} catch(Throwable e) {e.printStackTrace();}

        GeneratorTypeAccessor.getValues().add(ELTREALISTIC);
        Handler_Worldgen Handler_WORLDGEN = new Handler_Worldgen();
        Handler_Components Handler_COMPONENTS = new Handler_Components();
        Handler_WORLDGEN.setBiomesRidge(new ArrayList<Biome>());
        Handler_COMPONENTS.registerComponents();

        // Register all item and blocks
        new Handler_Items();
        new Handler_Blocks();

//        ELTCORE_Main.PROTON.getTag().values.clear();
//
//        ELTCORE_Main.PROTON.getTag().add(new Identifier("eltcore:oak_branch"));

//        ELTRESOURCE.addTag(new Identifier("c", "items/" + ELTCORE_Main.PROTON.mNameInternal), ELTCORE_Main.PROTON.getTag());


        // Register all the tags to RRP
        for (Map.Entry<String, ELTTag> entry : ELTTag.TAG_MAP.entrySet()) {
            ELTRESOURCE.addTag(new Identifier("c", "items/" + entry.getKey()), entry.getValue().getTag());
        }

        // Init RRP
        RRPCallback.EVENT.register(a -> a.add(ELTRESOURCE));

        LOGGER.info("---Energy Level Transition Initialized!---");
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }
}