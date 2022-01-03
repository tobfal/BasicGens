package de.tobfal.basicgens;

import de.tobfal.basicgens.init.ModBlockEntities;
import de.tobfal.basicgens.init.ModBlocks;
import de.tobfal.basicgens.init.ModItems;
import de.tobfal.basicgens.init.ModMenuTypes;
import de.tobfal.basicgens.screen.GeneratorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BasicGens.MOD_ID)
public class BasicGens
{
    public static final String MOD_ID = "basicgens";

    private static final Logger LOGGER = LogManager.getLogger();

    public BasicGens() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        MenuScreens.register(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.STONE_GENERATOR.get(), RenderType.cutout());

    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("[BasicGens] PreInit");
    }

}
