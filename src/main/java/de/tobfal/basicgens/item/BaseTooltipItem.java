package de.tobfal.basicgens.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseTooltipItem extends Item {

    //<editor-fold desc="Properties">
    private Component tooltipComponent;
    private Component shiftTooltipComponent;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BaseTooltipItem(Properties pProperties, Component tooltipComponent) {
        this(pProperties, tooltipComponent, null);
    }

    public BaseTooltipItem(Properties pProperties, Component tooltipComponent, Component shiftTooltipComponent) {
        super(pProperties);
        this.tooltipComponent = tooltipComponent;
        this.shiftTooltipComponent = shiftTooltipComponent;
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown() && shiftTooltipComponent != null) {
            pTooltipComponents.add(shiftTooltipComponent);
        } else {
            pTooltipComponents.add(tooltipComponent);
        }
    }
    //</editor-fold>
}
