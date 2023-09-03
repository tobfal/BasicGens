package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> BASICGENS_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BasicGens.MOD_ID);

    public static final List<Supplier<? extends ItemLike>> BASICGENS_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> INFUNDERE_TAB = BASICGENS_TABS.register("basicgenstab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.IRON_GENERATOR.get()))
            .title(Component.translatable("creativetab.basicgens.basicgenstab"))
            .displayItems((pParameters, pOutput) -> {
                BASICGENS_TAB_ITEMS.forEach(itemLike -> pOutput.accept(itemLike.get()));
            })
            .build()
    );

    //<editor-fold desc="Methods">
    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        BASICGENS_TAB_ITEMS.add(itemLike);
        return itemLike;
    }
    //</editor-fold>

    public static void register(IEventBus eventBus) {
        BASICGENS_TABS.register(eventBus);
    }
}
