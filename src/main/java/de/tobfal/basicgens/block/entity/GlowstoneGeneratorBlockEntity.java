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

public class GlowstoneGeneratorBlockEntity extends BaseFluidGeneratorBlockEntity {

    //<editor-fold desc="Constructor">
    public GlowstoneGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.GLOWSTONE_GENERATOR.get(), pWorldPosition, pBlockState, Fluids.LAVA, Config.GLOWSTONE_GENERATOR_EFFICIENCY.get(),
                Config.GLOWSTONE_GENERATOR_CAPACITY.get(), Config.GLOWSTONE_GENERATOR_PERTICK.get(), Config.GLOWSTONE_GENERATOR_TRANSFER.get());
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.basicgens.glowstone_generator");
    }
    //</editor-fold>
}
