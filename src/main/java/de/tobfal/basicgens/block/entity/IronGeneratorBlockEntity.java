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

public class IronGeneratorBlockEntity extends GeneratorBlockEntityBase {

    public IronGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.IRON_GENERATOR.get(), pWorldPosition, pBlockState, 1,
                Config.IRON_GENERATOR_CAPACITY.get(), Config.IRON_GENERATOR_PERTICK.get(), Config.IRON_GENERATOR_TRANSFER.get());
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Iron Generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new GeneratorMenu(pContainerId, pInventory, this, this.data);
    }
}
