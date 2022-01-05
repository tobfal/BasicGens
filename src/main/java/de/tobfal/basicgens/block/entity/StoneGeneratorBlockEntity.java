package de.tobfal.basicgens.block.entity;

import de.tobfal.basicgens.block.menu.GeneratorMenu;
import de.tobfal.basicgens.init.Config;
import de.tobfal.basicgens.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StoneGeneratorBlockEntity extends GeneratorBlockEntityBase {

    public StoneGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.STONE_GENERATOR.get(), pWorldPosition, pBlockState, Config.STONE_GENERATOR_EFFICIENCY.get(),
                Config.STONE_GENERATOR_CAPACITY.get(), Config.STONE_GENERATOR_PERTICK.get(), Config.STONE_GENERATOR_TRANSFER.get());
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Stone Generator");
    }

    @Nullable
    @Override
    public GeneratorMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new GeneratorMenu(pContainerId, pInventory, this, this.data);
    }
}
