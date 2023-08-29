package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BasicGens.MOD_ID);

    public static final RegistryObject<Item> CONTROLLER_AUGMENT = ModCreativeTabs.addToTab(ITEMS.register("controller_augment",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if (Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.basicgens.controller_augment.shift"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.basicgens.controller_augment"));
                    }
                }
            }));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
