package de.tobfal.basicgens.block.entity;

import de.tobfal.basicgens.block.menu.GeneratorMenu;
import de.tobfal.basicgens.init.Config;
import de.tobfal.basicgens.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GoldGeneratorBlockEntity extends GeneratorBlockEntityBase {

    public GoldGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.GOLD_GENERATOR.get(), pWorldPosition, pBlockState, Config.GOLD_GENERATOR_EFFICIENCY.get(),
                Config.GOLD_GENERATOR_CAPACITY.get(), Config.GOLD_GENERATOR_PERTICK.get(), Config.GOLD_GENERATOR_TRANSFER.get());
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Gold Generator");
    }

    @Nullable
    @Override
    public GeneratorMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new GeneratorMenu(pContainerId, pInventory, this, this.data);
    }
}
