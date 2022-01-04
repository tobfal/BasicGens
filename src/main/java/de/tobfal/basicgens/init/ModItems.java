package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BasicGens.MOD_ID);

    // Register Items

    public static final RegistryObject<Item> CONTROLLER_AUGMENT = ITEMS.register("controller_augment",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

    //---------------

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
