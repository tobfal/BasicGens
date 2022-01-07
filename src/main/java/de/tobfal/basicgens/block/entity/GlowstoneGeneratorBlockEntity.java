package de.tobfal.basicgens.block.entity;

import de.tobfal.basicgens.block.menu.FluidGeneratorMenu;
import de.tobfal.basicgens.init.Config;
import de.tobfal.basicgens.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class GlowstoneGeneratorBlockEntity extends FluidGeneratorBlockEntityBase {

    public GlowstoneGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.GLOWSTONE_GENERATOR.get(), pWorldPosition, pBlockState, Fluids.LAVA, Config.GLOWSTONE_GENERATOR_EFFICIENCY.get(),
                Config.GLOWSTONE_GENERATOR_CAPACITY.get(), Config.GLOWSTONE_GENERATOR_PERTICK.get(), Config.GLOWSTONE_GENERATOR_TRANSFER.get());
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Glowstone Generator");
    }

    @Nullable
    @Override
    public FluidGeneratorMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new FluidGeneratorMenu(pContainerId, pInventory, this, this.data);
    }
}
