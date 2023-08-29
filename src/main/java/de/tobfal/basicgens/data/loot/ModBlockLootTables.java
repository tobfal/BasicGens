package de.tobfal.basicgens.data.loot;

import de.tobfal.basicgens.init.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.STONE_GENERATOR.get());
        this.dropSelf(ModBlocks.IRON_GENERATOR.get());
        this.dropSelf(ModBlocks.GOLD_GENERATOR.get());
        this.dropSelf(ModBlocks.NETHER_GENERATOR.get());
        this.dropSelf(ModBlocks.GLOWSTONE_GENERATOR.get());
    }

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
