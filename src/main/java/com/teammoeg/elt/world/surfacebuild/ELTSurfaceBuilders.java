package com.teammoeg.elt.world.surfacebuild;

import com.teammoeg.elt.block.ELTBlocks;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ELTSurfaceBuilders {
    public static final SurfaceBuilderConfig ELTGRASSBLOCK = new SurfaceBuilderConfig(ELTBlocks.ELTGRASSBLOCK.defaultBlockState(), ELTBlocks.ELTDIRTBLOCK.defaultBlockState(), ELTBlocks.ELTDIRTBLOCK.defaultBlockState());
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> ELTGRASS = register("eltgrass", SurfaceBuilder.DEFAULT.configured(ELTGRASSBLOCK));

    private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String string_, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder_) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, string_, configuredSurfaceBuilder_);
    }
}
