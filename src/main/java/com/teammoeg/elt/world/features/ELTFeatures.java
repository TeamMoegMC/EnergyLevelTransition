package com.teammoeg.elt.world.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
    protected static final BlockState SPRUCE_LOG = Blocks.SPRUCE_LOG.defaultBlockState();
    protected static final BlockState SPRUCE_LEAVES = Blocks.SPRUCE_LEAVES.defaultBlockState();

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SPRUCE = register("spruce", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(SPRUCE_LOG), new SimpleBlockStateProvider(SPRUCE_LEAVES), new SpruceFoliagePlacer(FeatureSpread.of(2, 1), FeatureSpread.of(0, 2), FeatureSpread.of(1, 1)), new StraightTrunkPlacer(5, 2, 1), new TwoLayerFeature(2, 0, 2))).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PINE = register("pine", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(SPRUCE_LOG), new SimpleBlockStateProvider(SPRUCE_LEAVES), new PineFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(1), FeatureSpread.of(3, 1)), new StraightTrunkPlacer(6, 4, 0), new TwoLayerFeature(2, 0, 2))).ignoreVines().build()));
    public static final ConfiguredFeature<?, ?> ELT_VEGETATION = register("elt_vegetation", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(PINE.weighted(0.33333334F)), SPRUCE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
