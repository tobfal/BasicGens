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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NetherGeneratorBlockEntity extends BaseFluidGeneratorBlockEntity {

    //<editor-fold desc="Constructor">
    public NetherGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.NETHER_GENERATOR.get(), pWorldPosition, pBlockState, Fluids.LAVA, Config.NETHER_GENERATOR_EFFICIENCY.get(),
                Config.NETHER_GENERATOR_CAPACITY.get(), Config.NETHER_GENERATOR_PERTICK.get(), Config.NETHER_GENERATOR_TRANSFER.get());
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.basicgens.nether_generator");
    }
    //</editor-fold>
}
