package de.tobfal.basicgens;

import de.tobfal.basicgens.client.screen.FluidGeneratorScreen;
import de.tobfal.basicgens.client.screen.GeneratorScreen;
import de.tobfal.basicgens.init.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BasicGens.MOD_ID)
public class BasicGens {

    //<editor-fold desc="Constants">
    public static final String MOD_ID = "basicgens";
    private static final Logger LOGGER = LogManager.getLogger();
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BasicGens() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Config.init();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModCreativeTabs.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    private void clientSetup(final FMLClientSetupEvent event) {

        MenuScreens.register(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
        MenuScreens.register(ModMenuTypes.FLUID_GENERATOR_MENU.get(), FluidGeneratorScreen::new);

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.STONE_GENERATOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_GENERATOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHER_GENERATOR.get(), RenderType.cutout());
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("[BasicGens] PreInit");
    }
    //</editor-fold>
}
