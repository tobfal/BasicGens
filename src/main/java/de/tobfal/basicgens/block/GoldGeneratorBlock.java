package de.tobfal.basicgens.block;

import de.tobfal.basicgens.block.entity.GoldGeneratorBlockEntity;
import de.tobfal.basicgens.init.Config;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class GoldGeneratorBlock extends BaseGeneratorBlock {

    //<editor-fold desc="Constructor">
    public GoldGeneratorBlock(Properties properties) {
        super(properties);
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> components, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.basicgens.generator.description"));
            components.add(Component.translatable("tooltip.basicgens.generator.capacity").append(String.format(" %.0f kRF", Config.GOLD_GENERATOR_CAPACITY.get() / 1000f)));
            components.add(Component.translatable("tooltip.basicgens.generator.pertick").append(" " + Config.GOLD_GENERATOR_PERTICK.get() + " RF/t"));
            components.add(Component.translatable("tooltip.basicgens.generator.transfer").append(" " + Config.GOLD_GENERATOR_TRANSFER.get() + " RF/t"));
        } else {
            components.add(Component.translatable("tooltip.basicgens.generator"));
        }
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GoldGeneratorBlockEntity(pPos, pState);
    }
    //</editor-fold>
}