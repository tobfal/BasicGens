package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BasicGens.MOD_ID);

    // Register Items


    //---------------

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
