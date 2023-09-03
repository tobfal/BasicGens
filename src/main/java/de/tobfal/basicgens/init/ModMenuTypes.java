package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import de.tobfal.basicgens.block.menu.FluidGeneratorMenu;
import de.tobfal.basicgens.block.menu.GeneratorMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BasicGens.MOD_ID);

    public static final RegistryObject<MenuType<GeneratorMenu>> GENERATOR_MENU = registerMenuType(GeneratorMenu::new, "generator_menu");
    public static final RegistryObject<MenuType<FluidGeneratorMenu>> FLUID_GENERATOR_MENU = registerMenuType(FluidGeneratorMenu::new, "fluid_generator_menu");

    //<editor-fold desc="Methods">
    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    //</editor-fold>

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
