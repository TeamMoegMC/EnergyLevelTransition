package com.teammoeg.elt.block;

import com.google.common.collect.ImmutableSet;
import com.teammoeg.elt.ELT;
import com.teammoeg.elt.block.researchtable.ResearchTableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.function.Supplier;

public class ELTTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES_REGISTRY =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ELT.MOD_ID);
    public static final RegistryObject<TileEntityType<ResearchTableTileEntity>> RESEARCHDESK = TILE_ENTITIES_REGISTRY.register(
            "research_table", makeType(ResearchTableTileEntity::new, () -> ELTBlocks.RESEARCH_DESK));

    public static final RegistryObject<TileEntityType<ResearchTableTileEntity>> RESEARCH_TABLE = TILE_ENTITIES_REGISTRY.register(
            "research_table", makeType(ResearchTableTileEntity::new, () -> ELTBlocks.RESEARCH_TABLE));

    private static <T extends TileEntity> Supplier<TileEntityType<T>> makeType(Supplier<T> create, Supplier<Block> valid) {
        return makeTypeMultipleBlocks(create, () -> ImmutableSet.of(valid.get()));
    }

    private static <T extends TileEntity> Supplier<TileEntityType<T>> makeTypeMultipleBlocks(
            Supplier<T> create,
            Supplier<Collection<Block>> valid) {
        return () -> new TileEntityType<>(create, ImmutableSet.copyOf(valid.get()), null);
    }
}
