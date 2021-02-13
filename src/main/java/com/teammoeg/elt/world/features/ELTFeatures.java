package com.teammoeg.elt.world.features;

import com.google.common.collect.ImmutableList;
import com.teammoeg.elt.block.ELTBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.PineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class ELTFeatures {
    protected static final BlockState ELT_SPRUCE_LOG = ELTBlocks.ELT_SPRUCEWOOD.defaultBlockState();
    protected static final BlockState ELT_SPRUCE_LEAVES = ELTBlocks.ELT_SPRUCELEAVES.defaultBlockState();

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SPRUCE = register("eltspruce", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ELT_SPRUCE_LOG), new SimpleBlockStateProvider(ELT_SPRUCE_LEAVES), new SpruceFoliagePlacer(FeatureSpread.of(3, 1), FeatureSpread.of(0, 3), FeatureSpread.of(1, 1)), new StraightTrunkPlacer(6, 3, 2), new TwoLayerFeature(2, 0, 2))).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PINE = register("eltpine", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ELT_SPRUCE_LOG), new SimpleBlockStateProvider(ELT_SPRUCE_LEAVES), new PineFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(2), FeatureSpread.of(4, 1)), new StraightTrunkPlacer(7, 4, 1), new TwoLayerFeature(2, 0, 2))).ignoreVines().build()));
    public static final ConfiguredFeature<?, ?> ELT_VEGETATION = register("elt_vegetation", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(PINE.weighted(0.33333334F)), SPRUCE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
