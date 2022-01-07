package de.tobfal.basicgens.data;

import de.tobfal.basicgens.init.ModBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {

        this.dropSelf(ModBlocks.STONE_GENERATOR.get());
        this.dropSelf(ModBlocks.IRON_GENERATOR.get());
        this.dropSelf(ModBlocks.GOLD_GENERATOR.get());
        this.dropSelf(ModBlocks.NETHER_GENERATOR.get());
        this.dropSelf(ModBlocks.GLOWSTONE_GENERATOR.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
