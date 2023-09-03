package de.tobfal.basicgens.block.entity;

import de.tobfal.basicgens.block.menu.GeneratorMenu;
import de.tobfal.basicgens.init.Config;
import de.tobfal.basicgens.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IronGeneratorBlockEntity extends BaseGeneratorBlockEntity {

    //<editor-fold desc="Constructor">
    public IronGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.IRON_GENERATOR.get(), pWorldPosition, pBlockState, Config.IRON_GENERATOR_EFFICIENCY.get(),
                Config.IRON_GENERATOR_CAPACITY.get(), Config.IRON_GENERATOR_PERTICK.get(), Config.IRON_GENERATOR_TRANSFER.get());
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.basicgens.iron_generator");
    }
    //</editor-fold>
}
