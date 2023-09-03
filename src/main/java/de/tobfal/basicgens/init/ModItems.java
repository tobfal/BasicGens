package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import de.tobfal.basicgens.item.BaseTooltipItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BasicGens.MOD_ID);

    public static final RegistryObject<Item> CONTROLLER_AUGMENT = ModCreativeTabs.addToTab(ITEMS.register("controller_augment",
            () -> new BaseTooltipItem(new Item.Properties().stacksTo(1),
                    Component.translatable("tooltip.basicgens.shift"),
                    Component.translatable("tooltip.basicgens.controller_augment")
            )));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
