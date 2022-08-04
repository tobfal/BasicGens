package de.tobfal.basicgens.block.entity;

import de.tobfal.basicgens.block.menu.FluidGeneratorMenu;
import de.tobfal.basicgens.init.Config;
import de.tobfal.basicgens.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class NetherGeneratorBlockEntity extends FluidGeneratorBlockEntityBase {

    public NetherGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.NETHER_GENERATOR.get(), pWorldPosition, pBlockState, Fluids.LAVA, Config.NETHER_GENERATOR_EFFICIENCY.get(),
                Config.NETHER_GENERATOR_CAPACITY.get(), Config.NETHER_GENERATOR_PERTICK.get(), Config.NETHER_GENERATOR_TRANSFER.get());
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Nether Generator");
    }

    @Nullable
    @Override
    public FluidGeneratorMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new FluidGeneratorMenu(pContainerId, pInventory, this, this.data);
    }
}
